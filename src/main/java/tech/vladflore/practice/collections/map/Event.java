package tech.vladflore.practice.collections.map;

import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class Event {
    @NonNull
    private ZonedDateTime eventTime;
    private String content;
}
