package ru.regiuss.practice.dictinoary.server.controller.api.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.regiuss.practice.dictinoary.server.dto.PageDTO;
import ru.regiuss.practice.dictinoary.server.dto.ResponseDTO;
import ru.regiuss.practice.dictinoary.server.dto.WordDTO;
import ru.regiuss.practice.dictinoary.server.mapper.WordMapper;
import ru.regiuss.practice.dictinoary.server.model.Dictionary;
import ru.regiuss.practice.dictinoary.server.model.Page;
import ru.regiuss.practice.dictinoary.server.model.Word;
import ru.regiuss.practice.dictinoary.server.service.DictionaryService;
import ru.regiuss.practice.dictinoary.server.util.Utils;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/dictionary")
public class DictionaryController {

    private final DictionaryService service;
    private final WordMapper mapper;

    @GetMapping
    public ResponseDTO<Map<Integer, String>> getDictionaries() {
        Dictionary[] dictionaries = service.getDictionaries();
        return new ResponseDTO<>(Utils.arrayToMap(dictionaries, Dictionary::getName));
    }

    @GetMapping("/{dictionaryID}")
    public ResponseDTO<PageDTO<WordDTO>> getWords(
            @PathVariable int dictionaryID,
            @RequestParam(defaultValue = "0") int skip,
            @RequestParam(defaultValue = "20") int count
    ) {
        Page<Word> page = service.getWords(dictionaryID-1, skip, count);
        PageDTO<WordDTO> dto = new PageDTO<>(
                page.getItems().stream().map(mapper).collect(Collectors.toList()),
                page.getTotal(),
                page.getSkip()
        );
        return new ResponseDTO<>(dto);
    }

    @PostMapping("/{dictionaryID}")
    public ResponseDTO<WordDTO> add(@PathVariable int dictionaryID, @RequestBody WordDTO dto) {
        Word word = service.add(new Word(dto.getKey(), dto.getValue()), dictionaryID-1);
        return new ResponseDTO<>(mapper.apply(word));
    }

    @DeleteMapping("/{dictionaryID}")
    public ResponseDTO<Void> delete(@PathVariable int dictionaryID, @RequestParam String key) {
        service.delete(dictionaryID-1, key);
        return new ResponseDTO<>(null);
    }

    @GetMapping("/{dictionaryID}/search")
    public ResponseDTO<WordDTO> search(@PathVariable int dictionaryID, @RequestParam String key) {
        Word word = service.search(dictionaryID-1, key);
        return new ResponseDTO<>(mapper.apply(word));
    }

}
