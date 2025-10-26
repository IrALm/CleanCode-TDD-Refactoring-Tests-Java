package com.a.introduction.gildedrose;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/*
*
* Voici les points d'amélioration que je souhaite apporter à ce fichier :
*
* 1. Donner des noms de tests explictes à ces deux méthodes pour comprendre
*    immédiatement la règle métier testé
*
* 2. Utilser @BeforeEach poour initier les attributs communs des tests
* 3. Je reduis la dupication : Plus besoin d’écrire app.items[0] à chaque fois, on utilise defaultItem à présent
*    Ainsi donc , chaque test sera concis et décrira exactement ce qu'il teste
*
* */

class GildedRoseADefaultItemTest {

    private GildedRose app;
    private Item defaultItem;

    @BeforeEach
    void setUp() {
        // Initialisation commune des tests
        defaultItem = new Item("DEFAULT_ITEM", 15, 3);
        app = new GildedRose(new Item[] { defaultItem });
    }

    @Test
    void should_decrease_quality_by_1_and_sellIn_by_1_for_non_expired_item() {
        app.updateQuality();

        assertEquals("DEFAULT_ITEM", defaultItem.name);
        assertEquals(14, defaultItem.sellIn);
        assertEquals(2, defaultItem.quality);
    }

    @Test
    void should_decrease_quality_by_2_and_sellIn_by_1_for_expired_item() {
        // je remplace defaultItem par un article expiré
        defaultItem.sellIn = -1;
        defaultItem.quality = 3;

        app.updateQuality();

        assertEquals("DEFAULT_ITEM", defaultItem.name);
        assertEquals(-2, defaultItem.sellIn);
        assertEquals(1, defaultItem.quality);
    }
}
