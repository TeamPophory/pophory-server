package com.pophory.pophorydomain.photo.infrastructure;

import com.pophory.pophorydomain.album.Album;
import com.pophory.pophorydomain.photo.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PhotoJpaRepository extends JpaRepository<Photo, Long> {

    List<Photo> findAllByAlbum(Album album);

    Optional<Photo> findByShareId(String shareId);

}
