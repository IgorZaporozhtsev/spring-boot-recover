package com.zeecoder.reboot.dto;

import com.zeecoder.reboot.model.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountListDto {

    private List<Account> accountList;
}
