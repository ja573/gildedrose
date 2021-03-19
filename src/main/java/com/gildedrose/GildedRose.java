package com.gildedrose;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (final Item item : items) {
            final CustomItem customItem = new CustomItem(item);
            customItem.updateItemQuality();
        }
    }

    class CustomItem {
        protected Item item;

        public CustomItem CustomItem(final Item item) {
            switch (item.name) {
            case "Aged Brie":
                return new AgedBrie(item);
            case "Backstage passes to a TAFKAL80ETC concert":
                return new BackStagePass(item);
            case "Sulfuras, Hand of Ragnaros":
                return new Sulfurus(item);
            default:
                return new StandardItem(item);
            }
        }

        public boolean isExpired() {
            return item.sellIn < 0;
        }

        public boolean isQualityLessThan50() {
            return item.quality < 50;
        }

        public boolean isItemEdible() {
            return item.quality > 0;
        }

        public void incrementQualityByOne() {
            this.changeQuality(1);
        }

        public void decrementQualityByOne() {
            this.changeQuality(-1);
        }

        public void makeItemStale() {
            this.item.quality = 0;
        }

        public void decrementSellInByOne() {
            item.sellIn = item.sellIn - 1;
        }

        public boolean isSellInLessThanEleven() {
            return item.sellIn < 11;
        }

        private void changeQuality(final int changeBy) {
            item.quality = item.quality + changeBy;
        }

        public void updateItemQuality() {
            if (item.name.equals("Aged Brie") && this.isQualityLessThan50()) {
                this.incrementQualityByOne();
            } else if ((item.name.equals("Backstage passes to a TAFKAL80ETC concert"))) {
                if (this.isQualityLessThan50()) {
                    this.incrementQualityByOne();

                    if (this.isQualityLessThan50() && this.isSellInLessThanEleven()) {
                        this.incrementQualityByOne();
                    }
                }
            } else if (this.isItemEdible() && !item.name.equals("Sulfuras, Hand of Ragnaros")) {
                this.decrementQualityByOne();
            }

            if (!item.name.equals("Sulfuras, Hand of Ragnaros")) {
                this.decrementSellInByOne();
            }

            if (this.isExpired()) {
                if (item.name.equals("Aged Brie") && this.isQualityLessThan50()) {
                    this.incrementQualityByOne();
                } else if (item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                    this.makeItemStale();
                } else if (this.isItemEdible() && !item.name.equals("Sulfuras, Hand of Ragnaros")) {
                    this.decrementQualityByOne();
                }
            }
        }
    }

    class AgedBrie extends CustomItem {
        public AgedBrie(final Item item) {
            this.item = item;
        }

        public void updateItemQuality() {
            if (this.isQualityLessThan50()) {
                this.incrementQualityByOne();
            }

            this.decrementSellInByOne();

            if (this.isExpired() && this.isQualityLessThan50()) {
                this.incrementQualityByOne();
            }
        }
    }

    class BackStagePass extends CustomItem {
        public BackStagePass(final Item item) {
            this.item = item;
        }

        public void updateItemQuality() {
            if (this.isQualityLessThan50()) {
                this.incrementQualityByOne();

                if (this.isSellInLessThanEleven()) {
                    this.incrementQualityByOne();
                }
            }

            this.decrementSellInByOne();

            if (this.isExpired()) {
                this.makeItemStale();
            }
        }
    }

    class Sulfurus extends CustomItem {
        public Sulfurus(final Item item) {
            this.item = item;
        }

        public void updateItemQuality() { }
    }

    class StandardItem extends CustomItem {
        public StandardItem(final Item item) {
            this.item = item;
        }

        public void updateItemQuality() {
            if (this.isItemEdible()) {
                this.decrementQualityByOne();
            }

            this.decrementSellInByOne();

            if (this.isExpired() && this.isItemEdible()) {
                this.decrementQualityByOne();
            }
        }
    }
}