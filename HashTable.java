public class HashTable<K, V> {
    private static final int initBasketSize = 10;
    private Basket basketArray[];
    private int size;
    private final double loadFactor = 0.75;

    private class Entity {
        private K key;
        private V value;

        public Entity(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private class Basket {
        private class BasketNode {
            private Entity entity;
            private BasketNode next;

            public BasketNode(K key, V value) {
                entity = new Entity(key, value);
            }
        }

        private BasketNode head;

        public V find(K key) {
            BasketNode currentNode = head;
            while (currentNode != null) {
                if (currentNode.entity.key.equals(key)) {
                    return currentNode.entity.value;
                }
                currentNode = currentNode.next;
            }
            return null;
        }

        public boolean add(K key, V value) {
            BasketNode currentNode = head;
            BasketNode basketNode = new BasketNode(key, value);
            while (currentNode != null) {
                if (currentNode.entity.key.equals(key)) {
                    return false;
                } else if (currentNode.next == null) {
                    currentNode.next = basketNode;
                    return true;
                }
                currentNode = currentNode.next;
            }
            head = basketNode;
            return true;
        }

        public boolean remove(K key) {
            BasketNode currentNode = head;
            if (currentNode == null)
                return false;
            else if (currentNode.entity.key.equals(key)) {
                head = head.next;
                return true;
            }
            while (currentNode.next != null) {
                if (currentNode.next.entity.key.equals(key)) {
                    currentNode.next = currentNode.next.next;
                    return true;
                }
                currentNode = currentNode.next;
            }
            return false;
        }
    }

    public HashTable() {
        this(initBasketSize);
    }

    public HashTable(int initSize) {
        basketArray = (Basket[]) new Object[initSize];
    }

    private int calculateIndex(K key) {
        return Math.abs(key.hashCode()) % initBasketSize;
    }

    public V find(K key) {
        Basket basket = basketArray[calculateIndex(key)];
        if (basket != null)
            return basket.find(key);
        return null;
    }

    public boolean add(K key, V value) {
        if (loadFactor * basketArray.length > size)
            resize();
        int calculatedIndex = calculateIndex(key);
        Basket basket = basketArray[calculatedIndex];
        if (basket == null) {
            basketArray[calculatedIndex] = new Basket();
        }
        boolean valueAdded = basketArray[calculatedIndex].add(key, value);
        if (valueAdded)
            size++;
        return valueAdded;
    }

    public boolean remove(K key) {
        Basket basket = basketArray[calculateIndex(key)];
        if (basket == null)
            return false;
        else {
            boolean valueRemoved = basket.remove(key);
            if (valueRemoved)
                size--;
            return valueRemoved;
        }
    }

    private void resize() {
        Basket[] oldBasket = basketArray;
        basketArray = (Basket[]) new Object[oldBasket.length * 2];
        size = 0;
        for (Basket basket : oldBasket) {
            if (basket != null) {
                Basket.BasketNode basketNode = basket.head;
                while (basketNode != null) {
                    add(basketNode.entity.key, basketNode.entity.value);
                    basketNode = basketNode.next;
                }
            }
        }
    }
}