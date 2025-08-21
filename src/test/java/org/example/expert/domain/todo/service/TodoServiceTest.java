package org.example.expert.domain.todo.service;

import jakarta.persistence.EntityManager;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.todo.repository.TodoRepository;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.enums.UserRole;
import org.example.expert.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class TodoServiceTest {
    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private EntityManager em;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void todo_NplusOne_test() {
        //given

        AuthUser authUser1 = new AuthUser(1L,"email1", UserRole.USER);
        AuthUser authUser2 = new AuthUser(2L, "email2", UserRole.USER);
        User user1 =  User.fromAuthUser(authUser1);
        User user2 =  User.fromAuthUser(authUser2);
        Todo todo1 = new Todo("title1","contents1","weather1", user1);
        Todo todo2 = new Todo("title2","contents2","weather2", user2);
        userRepository.save(user1);
        userRepository.save(user2);
        todoRepository.save(todo1);
        todoRepository.save(todo2);
        Pageable pageable = PageRequest.of(0, 10);

        em.flush();
        em.clear();

        //when then
        System.out.println("----------------------------------------------------------");
        Page<Todo> todos = todoRepository.findAllByOrderByModifiedAtDesc(pageable);

    }
}
