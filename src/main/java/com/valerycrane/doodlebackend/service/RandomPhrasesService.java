package com.valerycrane.doodlebackend.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class RandomPhrasesService {

    private final List<String> phrases = Arrays.asList(
            "The eraser erases the pencil, the eraser erases the chalk",
            "The big box on the road",
            "Grandma's house, surrounded by vines, the coffee is taking a long time, it's certainly not powdered",
            "Chinese food is made with sugar and spices, and lots of rice",
            "Nobody's favorite food is cabbage",
            "Education is important, but big muscles are more important",
            "The entrance is guarded by a myopic fire-breathing dragon with a fear of heights",
            "The entrance is guarded by a seer and seer dragon with a fear of heights",
            "The store ran out of yoga mats",
            "The easiest way to make a grown man cry is to force him to watch Toy Story 3",
            "The door closed behind the poor",
            "The low light looks dreary",
            "The queen sat on her throne and ate prickly kumquats",
            "The quick brown fox jumps on the lazy dog and says hello to the cat",
            "The secret password is cock-a-doodle-doo-diddle-doodad",
            "The eiffel tower at my uncle's house",
            "The only thing better than a tall, dark, handsome man is carrying a pizza box",
            "The only thing I enjoy more than reading a book is watching the movie and never reading the book",
            "The spotted cow was wet by another spotted cow"
    );

    public List<String> generateRandomPhrases(int count) {
        Random random = new Random();
        List<String> result = new ArrayList<>();
        for (int i = 0; i < count; ++i) {
            int randomIndex = random.nextInt(phrases.size());
            result.add(phrases.get(randomIndex));
        }
        return result;
    }
}
