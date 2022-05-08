package ir.manoosheh.mylinkedin.controller;

import ir.manoosheh.mylinkedin.model.Media;
import ir.manoosheh.mylinkedin.payload.FileInfo;
import ir.manoosheh.mylinkedin.repository.MediaRepository;
import ir.manoosheh.mylinkedin.service.FilesStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    FilesStorageService storageService;

    @Autowired
    MediaRepository mediaRepository;


    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            message = storageService.save(file);

//            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    @PostMapping("/uploadVideo")
    public @ResponseBody
    ResponseEntity<?> uploadVideo(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            message = storageService.save(file);

            // message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }


    @GetMapping("/files")
    public ResponseEntity<List<FileInfo>> getListFiles() {
        List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(FileController.class, "getFile", path.getFileName().toString()).build().toString();

            return new FileInfo(filename, url);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    @GetMapping("/downloads/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = storageService.load(filename);
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "video/mp4");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"");
        //headers.add("Content-Length", Long.toString(file.length()));
        return ResponseEntity.ok()
                .headers(headers).body(file);

    }

    @GetMapping("/download/{filename:.+}")
    @ResponseBody
    public ResponseEntity<byte[]> getFile2(@PathVariable String filename) {
        Media media = mediaRepository.getByFilename(filename).orElse(new Media());
        ResponseEntity<byte[]> result = null;
        try {
            Path root = Paths.get("uploads");
            Path path = root.resolve(filename);

            byte[] image = Files.readAllBytes(path);


            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", media.getType());
            headers.setContentLength(media.getSize());
            result = new ResponseEntity<byte[]>(image, headers, HttpStatus.OK);
        } catch (java.nio.file.NoSuchFileException e) {
//            response.setStatus(HttpStatus.NOT_FOUND.value());
        } catch (Exception e) {
//            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return result;
    }

}
