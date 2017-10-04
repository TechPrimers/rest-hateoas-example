package com.techprimers.rest.resthateoas.resource;

import com.techprimers.rest.resthateoas.model.Users;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/rest/users")
public class UsersResource {


    @GetMapping("/all")
    public List<Users> getAll() {
        Users users1 = getUser();
        Users users2 = new Users("Sam", 2400L);
        return Arrays.asList(users1, users2);
    }

    private Users getUser() {
        Users users = new Users("Peter", 2300L);
        Link link = ControllerLinkBuilder.linkTo(UsersResource.class)
                .slash(users.getName())
                .withSelfRel();
        users.add(link);
        return users;
    }

    @GetMapping(value = "/hateoas/all", produces = MediaTypes.HAL_JSON_VALUE)
    public List<Users> getHateOASAll() {
        Users users1 = new Users("Peter", 2300L);
        Link link = ControllerLinkBuilder.linkTo(UsersResource.class)
                .slash(users1.getSalary()).withSelfRel();
        Link link2 = ControllerLinkBuilder.linkTo(UsersResource.class)
                .slash(users1.getSalary()).withRel("salary");
        users1.add(link, link2);
        Users users2 = new Users("Sam", 2400L);
        users2.add(ControllerLinkBuilder.linkTo(UsersResource.class)
                .slash(users2.getSalary()).withSelfRel());
        return Arrays.asList(users1, users2);
    }
}
