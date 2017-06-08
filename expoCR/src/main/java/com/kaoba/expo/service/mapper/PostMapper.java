package com.kaoba.expo.service.mapper;

import com.kaoba.expo.domain.*;
import com.kaoba.expo.service.dto.PostDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Post and its DTO PostDTO.
 */
@Mapper(componentModel = "spring", uses = {TimelineMapper.class, })
public interface PostMapper extends EntityMapper <PostDTO, Post> {

    @Mapping(source = "timeline.id", target = "timelineId")
    PostDTO toDto(Post post); 

    @Mapping(source = "timelineId", target = "timeline")
    Post toEntity(PostDTO postDTO); 
    default Post fromId(Long id) {
        if (id == null) {
            return null;
        }
        Post post = new Post();
        post.setId(id);
        return post;
    }
}
