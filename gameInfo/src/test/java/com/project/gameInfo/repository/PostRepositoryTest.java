package com.project.gameInfo.repository;

import com.project.gameInfo.config.TestConfig;
import com.project.gameInfo.controller.dto.CategoryDto;
import com.project.gameInfo.controller.dto.MemberDto;
import com.project.gameInfo.controller.dto.PostDto;
import com.project.gameInfo.domain.Category;
import com.project.gameInfo.domain.Member;
import com.project.gameInfo.domain.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestConfig.class)
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    
    @Test
    @DisplayName("게시물 저장")
    void savePost() {

        //given
        Member member = generateMember();
        memberRepository.save(member);

        Category category = generateCategory();
        categoryRepository.save(category);

        Post post = generatePost(member, category);

        //when
        Post save = postRepository.save(post);

        //then
        assertThat(save).isSameAs(post);
        assertThat(save.getCategory()).isSameAs(category);
        assertThat(save.getMember()).isSameAs(member);
        assertThat(save.getId()).isEqualTo(post.getId());


    }


    Post generatePost(Member member, Category category){
        PostDto postDto = PostDto.builder()
                .title("title")
                .content("content")
                .build();

        return Post.createPost(postDto, category, member);
    }

    Category generateCategory() {
        CategoryDto categoryDto = CategoryDto.builder()
                .name("category")
                .build();

        return Category.createCategory(categoryDto);
    }

    Member generateMember(){
        MemberDto memberDto = MemberDto.builder()
                .memberId("member")
                .password("member")
                .nickname("member")
                .phone("010-1111-1111")
                .name("name")
                .roles("USER").build();
        return Member.createMember(memberDto);
    }

}