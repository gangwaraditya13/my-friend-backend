package com.adish.myfriend.Component;

import lombok.Data;

@Data
public class PasswordReset {
    private String oldPassword;
    private String newPassword;
}
