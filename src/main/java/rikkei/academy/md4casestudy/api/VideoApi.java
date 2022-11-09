package rikkei.academy.md4casestudy.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rikkei.academy.md4casestudy.dto.response.ResponseMessage;
import rikkei.academy.md4casestudy.model.User;
import rikkei.academy.md4casestudy.model.Video;
import rikkei.academy.md4casestudy.security.userprincipal.UserDetailsServiceIMPL;
import rikkei.academy.md4casestudy.service.video.IVideoService;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/api/manager/videos")
public class VideoApi {
    @Autowired
    private IVideoService videoService;
    @Autowired
    private UserDetailsServiceIMPL userDetailsServiceIMPL;

    @GetMapping
    public ResponseEntity<?> showAllVideo() {
        Iterable<Video> listVideo = videoService.findAll();
        return new ResponseEntity<>(listVideo, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createVideo(@Valid @RequestBody Video video) {
        User currenUser = userDetailsServiceIMPL.getCurrentUser();
        if (videoService.existsByName(video.getName())) {
            return new ResponseEntity<>(new ResponseMessage("video_invalid"), HttpStatus.OK);
        }
        video.setUser(currenUser);
        videoService.save(video);
        return new ResponseEntity<>(new ResponseMessage("create_success"), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editVideo(@PathVariable Long id, @RequestBody Video video) {
        Video video1 = videoService.findById(id);
        if (video1 == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        video1.setName(video.getName());
        videoService.save(video1);
        return new ResponseEntity<>(new ResponseMessage("update_success!"), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVideo(@PathVariable Long id) {
        Video video = videoService.findById(id);
        if (video == null) {
            return new ResponseEntity<>(new ResponseMessage("video does not exist!"), HttpStatus.OK);
        }
        videoService.deleteById(id);
        return new ResponseEntity<>(new ResponseMessage("delete_success!"), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVideoById(@PathVariable Long id) {
        return new ResponseEntity<>(videoService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/search/{search}")
    public ResponseEntity<?> search(
            @PathVariable String search
    ) {
        return ResponseEntity.ok(videoService.findByName(search));
    }
}
