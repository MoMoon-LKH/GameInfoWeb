package com.project.gameInfo.controller;

import com.project.gameInfo.controller.dto.IdList;
import com.project.gameInfo.controller.dto.PlatformDto;
import com.project.gameInfo.domain.Platform;
import com.project.gameInfo.service.PlatformService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/manage")
public class PlatformController {

    private final PlatformService platformService;


    @GetMapping("/platform/list")
    public ResponseEntity<?> getPlatformList(@PageableDefault(size = 20) Pageable pageable){

        return ResponseEntity.ok(platformService.findAllByPage(pageable));
    }

    @PostMapping("/platform/new")
    public ResponseEntity<?> createPlatform(@RequestBody PlatformDto platformDto) {

        if (platformService.findByName(platformDto.getName()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 존재하는 이름입니다");
        } else{

            Long save = platformService.save(platformDto.getName());
            return ResponseEntity.ok(save);
        }
    }

    @PutMapping("/platform")
    public ResponseEntity<?> updatePlatform(@RequestBody PlatformDto platformDto) {

        Platform platform = platformService.findById(platformDto.getId());

        platformService.updatePlatform(platform, platformDto.getName());

        return ResponseEntity.ok(
                PlatformDto.builder()
                        .id(platform.getId())
                        .name(platform.getName())
                .build()
        );
    }


    @DeleteMapping("/platform")
    public ResponseEntity<?> deletePlatforms(@RequestBody IdList ids) {

        List<Platform> platforms = platformService.findAllByIds(ids.getIds());

        boolean deleteBool = platformService.deleteAll(platforms);

        return ResponseEntity.ok(deleteBool);
    }

}
