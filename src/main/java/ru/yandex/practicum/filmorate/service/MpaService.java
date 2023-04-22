package ru.yandex.practicum.filmorate.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaDbStorage;

import java.util.List;


@Service("mpaService")
@Data
public class MpaService {

    MpaDbStorage mpaDbStorage;


    @Autowired
    public MpaService(@Qualifier("mpaDbStorage") MpaDbStorage mpaDbStorage) {
        this.mpaDbStorage = mpaDbStorage;
    }

    public MpaDbStorage getMpaDbStorage() {
        return mpaDbStorage;
    }

    public List<Mpa> getMpa() {
        return getMpaDbStorage().getMpa();
    }

    public Mpa getMpaById(Integer mpaId) {
        return getMpaDbStorage().getMpaById(mpaId);
    }

}
