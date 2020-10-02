package com.zeecoder.reboot.config;

import com.zeecoder.reboot.dto.AccountDto;
import com.zeecoder.reboot.model.Account;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AccountMapper {

    private final ModelMapper mapper;

    @Autowired
    public AccountMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public Account toEntity(AccountDto dto) {
        return Objects.isNull(dto) ? null : mapper.map(dto, Account.class);
    }

    public AccountDto toDto(Account entity) {
        return Objects.isNull(entity) ? null : mapper.map(entity, AccountDto.class);
    }
}
