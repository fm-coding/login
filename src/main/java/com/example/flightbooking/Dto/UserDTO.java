package com.example.flightbooking.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class UserDTO {
        private int userid;
        private String username;
        private String email;
        private String password;


        public UserDTO (int userid, String username, String email, String password)
        {
            this.userid = userid;
            this.username = username;
            this.email = email;
            this.password = password;
        }
}
