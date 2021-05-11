package com.learn.springboot.service.posts;

import com.learn.springboot.domain.posts.Boards;
import com.learn.springboot.domain.posts.BoardsRepository;
import com.learn.springboot.domain.posts.Posts;
import com.learn.springboot.domain.posts.PostsRepository;
import com.learn.springboot.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;
    private final BoardsRepository boardsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        //post 저장할 때 post의 boardname 필드값이 boards 테이블에 있는지 확인, 없으면 추가 있으면 count++
        if(boardsRepository.findBoardDesc(requestDto.getBoardName()).isEmpty()){
            boardsRepository.save(new Boards(requestDto.getBoardName(), 1L));
            requestDto.setNo(1L);
        }else{
            Boards boards = boardsRepository.findById(requestDto.getBoardName()).orElseThrow(() -> new IllegalArgumentException("Cannot find board name=" + requestDto.getBoardName()));
            boards.countUpdate();
            requestDto.setNo(boards.getPostsCount());
        }
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Cannot find post id=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    @Transactional
    public void delete (Long id){
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        postsRepository.delete(posts);
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Cannot find post id=" + id));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc(){
        return postsRepository.findAllDesc().stream().map(PostsListResponseDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findBoardDesc(String boardName){
        return postsRepository.findBoardDesc(boardName).stream().map(PostsListResponseDto::new).collect(Collectors.toList());
    }
}
