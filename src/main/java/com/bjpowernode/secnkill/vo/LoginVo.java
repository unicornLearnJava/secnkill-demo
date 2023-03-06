package com.bjpowernode.secnkill.vo;

import com.bjpowernode.secnkill.validations.isMobulie;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginVo {
    @NotNull
    @isMobulie
    private String mobile;
    @NotNull
    @Length(min = 32)
    private String password;
}
