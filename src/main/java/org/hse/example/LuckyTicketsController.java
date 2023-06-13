package org.hse.example;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import org.hse.example.entities.TicketEntity;
import org.hse.example.repositories.TicketEntityRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Контроллер для работы со счастливыми билетами
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/lucky-tickets")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LuckyTicketsController {
    Map<String, Counter> counters;
    TicketEntityRepository repository;
    @GetMapping
    public ResponseEntity<Integer> getTicketsQuantity(@RequestParam final Integer length) {
        Optional<Counter> counter = getCounter(length);
        return counter.map(Counter::getCount).map(ResponseEntity::ok).orElseGet(ResponseEntity.notFound()::build);
    }

    @PostMapping
    public ResponseEntity<Collection<PostResponseBody>> getTicketsQuantity(@RequestBody final Collection<Integer> lengths) {
        var result =
                lengths
                        .stream()
                        .map(this::getResponseBodyByLength)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    private Optional<Counter> getCounter(Integer length) {
        return switch (length) {
                    case 6 -> Optional.ofNullable(counters.get("counterSix"));
                    case 8 -> Optional.ofNullable(counters.get("counterEight"));
                    default -> Optional.empty();
               };
    }

    @GetMapping("/cache/{length}")
    public Collection<TicketEntity> getTicketsCache(@PathVariable final Integer length) {
        return repository.findAllByLength(length);
    }

    private Optional<PostResponseBody> getResponseBodyByLength(final Integer length) {
        return getCounter(length)
                .map(Counter::getCount)
                .map(PostResponseBody.builder().length(length)::count)
                .map(PostResponseBody.PostResponseBodyBuilder::build);
    }

    @Value
    @Builder
    static class PostResponseBody {
        int length;
        int count;
    }

}
