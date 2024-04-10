package com.tatechsoft.project.module.ums.model;

import com.tatechsoft.project.database.entity.User;
import lombok.Data;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserProfileDto {
    private User user;
    private Profile profile;
    private List<String> roles = new ArrayList<>();

}
