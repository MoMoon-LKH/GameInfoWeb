package com.project.gameInfo.service;

import com.project.gameInfo.domain.Image;
import com.project.gameInfo.exception.NotFoundCommentException;
import com.project.gameInfo.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;


    @Transactional
    public Long save(Image image) {
        return imageRepository.save(image).getId();
    }

    public String findUrl(Long id) {
        return imageRepository.findById(id).orElseThrow(NoSuchFieldError::new).getUrl();
    }
}
