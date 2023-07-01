package com.pophory.pophoryserver.domain.photo;

import com.pophory.pophoryserver.domain.album.Album;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoJpaRepository extends JpaRepository<Photo, Long> {

    List<Photo> findAllByAlbum(Album album);
}
