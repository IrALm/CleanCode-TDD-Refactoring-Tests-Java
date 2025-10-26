package com.a.introduction.gildedrose;

import java.util.Arrays;

/*
*
* Pour cette classe , j'ai égalemnt decidé de le refactoriser complètement
* Objectifs :
*
* 1. Magic strings remplacés par des constantes :
*    pour les noms des articles (AGED_BRIE, BACKSTAGE_PASS, SULFURAS)
*    Plus facile à maintenir.

* 2. Conditions imbriquées simplifiées :
*    Méthodes dédiées pour chaque comportement
        .updateItemQuality() : logique principale par type d’article.
        .updateQualityAfterSellIn() : ajustements après la date de péremption.
        .increaseQuality() et decreaseQuality() : encapsulent les limites (0–50).

* 3. Boucle plus lisible :
*    .Boucle for-each plus lisible
     .Plus besoin d’index i.
     .Tout se fait via l’objet item.

* 4. STANDARD_QUALITY_CHANGE , MAX_QUALITY
*    BACKSTAGE_SELLIN_THRESHOLD_10 ,SELLIN_DECREMENT,
*    BACKSTAGE_SELLIN_THRESHOLD_10 et MIN_QUALITY évitent les magic numbers (50 et 0).
* */

class GildedRose {

    private static final String AGED_BRIE = "Aged Brie";
    private static final String BACKSTAGE_PASS = "Backstage passes";
    private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";

    private static final int MAX_QUALITY = 50;
    private static final int MIN_QUALITY = 0;
    private static final int STANDARD_QUALITY_CHANGE = 1;
    private static final int SELLIN_DECREMENT = 1;
    private static final int BACKSTAGE_SELLIN_THRESHOLD_10 = 10;
    private static final int BACKSTAGE_SELLIN_THRESHOLD_5 = 5;


    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public static void main(String[] args) {
        Item[] items = new Item[] { new Item("Default Item", 10, 20) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        System.out.println(app);
    }

    public void updateQuality() {
        for (Item item : items) {
            if (!item.name.equals(SULFURAS)) {
                updateItemQuality(item);
                decreaseSellIn(item);
                updateQualityAfterSellIn(item);
            }
        }
    }

    private void updateItemQuality(Item item) {
        if (item.name.equals(AGED_BRIE)) {
            increaseQuality(item, STANDARD_QUALITY_CHANGE);
        } else if (item.name.equals(BACKSTAGE_PASS)) {
            increaseQuality(item, STANDARD_QUALITY_CHANGE);
            if (item.sellIn <= BACKSTAGE_SELLIN_THRESHOLD_10)
                increaseQuality(item, STANDARD_QUALITY_CHANGE);
            if (item.sellIn <= BACKSTAGE_SELLIN_THRESHOLD_5)
                increaseQuality(item, STANDARD_QUALITY_CHANGE);
        } else {
            decreaseQuality(item, STANDARD_QUALITY_CHANGE);
        }
    }


    private void updateQualityAfterSellIn(Item item) {
        if (item.sellIn < 0) {
            if (item.name.equals(AGED_BRIE)) {
                increaseQuality(item, STANDARD_QUALITY_CHANGE);
            } else if (item.name.equals(BACKSTAGE_PASS)) {
                item.quality = MIN_QUALITY;
            } else {
                decreaseQuality(item, STANDARD_QUALITY_CHANGE);
            }
        }
    }

    private void increaseQuality(Item item, int amount) {
        item.quality = Math.min(item.quality + amount, MAX_QUALITY);
    }

    private void decreaseQuality(Item item, int amount) {
        item.quality = Math.max(item.quality - amount, MIN_QUALITY);
    }

    private void decreaseSellIn(Item item) {
        item.sellIn -= SELLIN_DECREMENT;
    }

    @Override
    public String toString() {
        return Arrays.toString(items);
    }
}
