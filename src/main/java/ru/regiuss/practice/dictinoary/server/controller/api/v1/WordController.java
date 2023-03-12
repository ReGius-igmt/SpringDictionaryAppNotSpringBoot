package ru.regiuss.practice.dictinoary.server.controller.api.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.regiuss.practice.dictinoary.server.dto.ResponseDTO;
import ru.regiuss.practice.dictinoary.server.dto.WordDTO;
import ru.regiuss.practice.dictinoary.server.mapper.WordMapper;
import ru.regiuss.practice.dictinoary.server.model.Word;
import ru.regiuss.practice.dictinoary.server.service.WordService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/word")
public class WordController {

    private final WordMapper mapper;
    private final WordService service;

    @GetMapping("/{wordID}")
    public ResponseDTO<WordDTO> get(@PathVariable int wordID) {
        Word word = service.get(wordID);
        return new ResponseDTO<>(mapper.apply(word));
    }

    @PutMapping("/{wordID}")
    public ResponseDTO<WordDTO> edit(@PathVariable int wordID, @RequestBody WordDTO dto) {
        Word word = service.edit(new Word(wordID, dto.getKey(), dto.getValues()));
        return new ResponseDTO<>(mapper.apply(word));
    }

    @DeleteMapping("/{wordID}")
    public ResponseDTO<Void> delete(@PathVariable int wordID) {
        service.delete(wordID);
        return new ResponseDTO<>(null);
    }
}
