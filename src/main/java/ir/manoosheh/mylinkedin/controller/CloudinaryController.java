package ir.manoosheh.mylinkedin.controller;

import ir.manoosheh.mylinkedin.service.CloudinaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/cloud")
public class CloudinaryController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CloudinaryService cloudinaryService;

    /**
     * Upload a MultipartFile to Cloudinary.
     *
     * @param file
     * @return the publicId assigned to the uploaded file, or null in case of
     * error
     */
    @PostMapping("/upload")
    public @ResponseBody
    String upload(@RequestParam("file") MultipartFile file) {
        return cloudinaryService.upload(file);
    }

    @PostMapping("/uploadVideo")
    public @ResponseBody
    String uploadVideo(@RequestParam("file") MultipartFile file) {
        return cloudinaryService.uploadVideo(file);
    }


    /**
     * Upload an Avatar to Cloudinary.
     *
     * @param isAvatarSinglePerson
     * @param file
     * @return the publicId assigned to the uploaded file, or null in case of
     * error
     */
    @PostMapping("/uploadAvatar")
    public @ResponseBody
    String uploadAvatar(@RequestHeader(value = "isAvatarSinglePerson") String isAvatarSinglePerson,
                        @RequestParam("file") MultipartFile file) {
        return cloudinaryService.uploadAvatar(isAvatarSinglePerson, file);
    }

    /**
     * Download an image from the cloud, scaled and cropped the given size
     *
     * @param publicId of the image, returned by the upload() method
     * @param width    in px
     * @param height   in px
     * @return
     */
    @GetMapping("/downloadImg/{publicId}/{width}/{height}")
    public @ResponseBody
    ResponseEntity<ByteArrayResource> downloadImg(@PathVariable String publicId,
                                                  @PathVariable int width,
                                                  @PathVariable int height) {
        return cloudinaryService.downloadImg(publicId, width, height, false);
    }

    /**
     * Download an rounded avatar from the cloud, scaled and cropped the given size
     *
     * @param publicId of the image, returned by the upload() method
     * @param size     in px
     * @return
     */
    @GetMapping("/downloadAvatar/{publicId}/{size}")
    public @ResponseBody
    ResponseEntity<ByteArrayResource> downloadAvatar(@PathVariable String publicId, @PathVariable int size) {
        return cloudinaryService.downloadImg(publicId, size, size, true);
    }

}