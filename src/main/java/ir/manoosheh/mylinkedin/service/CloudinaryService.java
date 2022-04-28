package ir.manoosheh.mylinkedin.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.Singleton;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import ir.manoosheh.mylinkedin.model.User;
import ir.manoosheh.mylinkedin.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URL;
import java.util.Map;

@Service
public class CloudinaryService {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Cloudinary cloudinary = Singleton.getCloudinary();

    @Autowired
    UserService userService;

    @Autowired
    private UserRepository userRepository;

    /**
     * Upload a MultipartFile to Cloudinary. More info:
     * https://stackoverflow.com/a/39572293
     *
     * @param file to be uploaded
     * @return the publicId assigned to the uploaded file, or null in case of
     * error
     */
    public String upload(MultipartFile file) {
        logger.trace("Called CloudinaryService.upload with args: ** and the multipart file");
        User user = userService.getUser();
        if (user != null) {
            try {
                Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
                String publicId = uploadResult.get("public_id").toString();
                logger.info("The user " + user.getEmail() + " successfully uploaded the file: " + publicId);
                String cloudUrl = cloudinary.url().secure(true)
                        .publicId(publicId)
                        .generate();
                return cloudUrl;
            } catch (Exception ex) {
                logger.error("The user " + user.getEmail() + " failed to load to Cloudinary the image file: " + file.getName());
                logger.error(ex.getMessage());
                return "Error" + ex.getMessage();
            }
        } else {
            logger.error("Error: a not authenticated user tried to upload a file (email: " + user.getEmail() + ")");
            return null;
        }
    }

    public String uploadVideo(MultipartFile file) {
        logger.trace("Called CloudinaryService.upload with args: ** and the multipart file");
        User user = userService.getUser();
        if (user != null) {
            try {
//                Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
                Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                        ObjectUtils.asMap("resource_type", "video"));
                String publicId = uploadResult.get("public_id").toString();
                logger.info("The user " + user.getEmail() + " successfully uploaded the file: " + publicId);
                String cloudUrl = cloudinary.url().secure(true)
                        .publicId(publicId)
                        .generate();
//                return cloudUrl;
                return uploadResult.get("url").toString();
            } catch (Exception ex) {
                logger.error("The user " + user.getEmail() + " failed to load to Cloudinary the image file: " + file.getName());
                logger.error(ex.getMessage());
                return "Error" + ex.getMessage();
            }
        } else {
            logger.error("Error: a not authenticated user tried to upload a file (email: " + user.getEmail() + ")");
            return null;
        }
    }

    /**
     * Upload an avatar and save its publicID in the User entity
     *
     * @param isAvatarSinglePerson
     * @param file                 to be uploaded
     * @return the publicId assigned to the uploaded file, or null in case of
     * error
     */
    public String uploadAvatar(String isAvatarSinglePerson, MultipartFile file) {
        User user = userService.getUser();
        if (user != null) {
            String publicId = upload(file);
            if ("true".equalsIgnoreCase(isAvatarSinglePerson)) {
                user.setImageUrl(publicId);
            } else {
                user.setImageUrl(publicId);
            }
            userRepository.save(user);
            logger.info("Saved the new avatar for the user: " + user.getEmail());
            return publicId;
        } else {
            logger.warn("Cannot authenticate the user " + user.getEmail() + " to upload him/her avatar");
            return null;
        }
    }

    /**
     * Download an image from Cloudinary
     *
     * @param publicId of the image
     * @param width
     * @param isAvatar
     * @param height
     * @return
     */
    public ResponseEntity<ByteArrayResource> downloadImg(String publicId, int width, int height, boolean isAvatar) {

        logger.info("Requested to download the image file: " + publicId);

        // Generates the URL
        String format = "jpg";
        Transformation transformation = new Transformation().width(width).height(height).crop("fill");
        if (isAvatar) {
            // transformation = transformation.gravity("face").radius("max");
            transformation = transformation.radius("max");
            format = "png";
        }
        String cloudUrl = cloudinary.url().secure(true).format(format)
                .transformation(transformation)
                .publicId(publicId)
                .generate();

        logger.debug("Generated URL of the image to be downloaded: " + cloudUrl);

        try {
            // Get a ByteArrayResource from the URL
            URL url = new URL(cloudUrl);
            InputStream inputStream = url.openStream();
            byte[] out = org.apache.commons.io.IOUtils.toByteArray(inputStream);
            ByteArrayResource resource = new ByteArrayResource(out);

            // Creates the headers
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("content-disposition", "attachment; filename=image.jpg");
            responseHeaders.add("Content-Type", "image/jpeg");

            return ResponseEntity.ok()
                    .headers(responseHeaders)
                    .contentLength(out.length)
                    // .contentType(MediaType.parseMediaType(mimeType))
                    .body(resource);

        } catch (Exception ex) {
            logger.error("FAILED to download the file: " + publicId);
            return null;
        }
    }
}
