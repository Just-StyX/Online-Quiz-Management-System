package jsl.group.quiz.utils.structures;

import java.util.*;

public class PositionalLinkedList<E> implements PositionList<E> {
    private final Random random = new Random();
    private static class Node<E> implements Position<E> {
        private Integer index;
        private E element;
        private Node<E> previousNode;
        private Node<E> nextNode;

        public Node(Integer index, E element, Node<E> previousNode, Node<E> nextNode) {
            this.index = index;
            this.element = element;
            this.previousNode = previousNode;
            this.nextNode = nextNode;
        }

        @Override
        public E getElement() {
            return element;
        }

        @Override
        public Integer getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public void setElement(E element) {
            this.element = element;
        }

        public Node<E> getPreviousNode() {
            return previousNode;
        }

        public void setPreviousNode(Node<E> previousNode) {
            this.previousNode = previousNode;
        }

        public Node<E> getNextNode() {
            return nextNode;
        }

        public void setNextNode(Node<E> nextNode) {
            this.nextNode = nextNode;
        }
    }
    private int size = 0;
    private final Node<E> header;
    private final Node<E> trailer;

    private Node<E> currentPositionOfElement;
    private PositionalLinkedList() {
        header = new Node<>(null, null, null, null);
        trailer = new Node<>(null, null, header, null);
        header.setNextNode(trailer);
    }
    public static <E> PositionalLinkedList<E> getInstance() {
        return new PositionalLinkedList<>();
    }
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public Position<E> first() {
        return position(header.getNextNode());
    }

    @Override
    public Position<E> last() {
        return position(trailer.getPreviousNode());
    }

    @Override
    public Position<E> after(Position<E> position) throws IllegalArgumentException {
        Node<E> node = validate(position);
        return position(node.getNextNode());
    }

    @Override
    public Position<E> before(Position<E> position) throws IllegalArgumentException {
        Node<E> node = validate(position);
        return position(node.getPreviousNode());
    }

    @Override
    public Position<E> addFirst(E element) {
        return addBetween(size, element, header, header.getNextNode());
    }

    @Override
    public Position<E> addLast(E element) {
        return addBetween(size, element, trailer.getPreviousNode(), trailer);
    }

    @Override
    public Position<E> addBefore(Position<E> position, E element) throws IllegalArgumentException {
        Node<E> node = validate(position);
        return addBetween(size, element, node.getPreviousNode(), node);
    }

    @Override
    public Position<E> addAfter(Position<E> position, E element) throws IllegalArgumentException {
        Node<E> node = validate(position);
        return addBetween(size, element, node, node.getNextNode());
    }

    @Override
    public E set(Position<E> position, E element) throws IllegalArgumentException {
        Node<E> node = validate(position);
        E nodeElement = node.getElement();
        node.setElement(element);
        return nodeElement;
    }

    @Override
    public E remove(Position<E> position) throws IllegalArgumentException {
        Node<E> node = validate(position);
        E nodeElement = node.getElement();
        Node<E> predecessor = node.getPreviousNode();
        Node<E> successor = node.getNextNode();
        predecessor.setNextNode(successor);
        successor.setPreviousNode(predecessor);
        size--;
        node.setElement(null);
        node.setNextNode(null);
        node.setPreviousNode(null);
        return nodeElement;
    }

    @Override
    public void sort(Comparator<E> comparator) {
        Position<E> marker = first();
        while (marker != last()) {
            Position<E> pivot = after(marker);
            E value = marker.getElement();
            if (comparator.compare(value, pivot.getElement()) > 0) {
                marker = pivot;
            } else {
                Position<E> walk = marker;
                while (walk != null && comparator.compare(before(walk).getElement(), value) > 0) {
                    walk = before(walk);
                }
                remove(pivot);
                addBefore(walk, value);
            }
        }
    }

    @Override
    public int indexOf(Position<E> position) {
        return validate(position).getIndex();
    }

    @Override
    public Position<E> findPosition(E element) {
        Position<E> walk = first();
        while (walk != null && !element.equals(walk.getElement())) {
            walk = after(walk);
        }
        return walk;
    }

    @Override
    public boolean contains(E element) {
        Position<E> walk = first();
        while (walk != last() && !element.equals(walk.getElement())) {
            walk = after(walk);
        }
        return walk.getElement().equals(element);
    }

    @Override
    public void clear() {
        Position<E> walk = first();
        while (walk != last()) {
            Position<E> position = walk;
            walk = after(walk);
            remove(position);
        }
        remove(last());
    }

    @Override
    public Position<E> positionAtIndex(int i) {
        Position<E> walk = first();
        while (walk != null && i != walk.getIndex()) {
            walk = after(walk);
        }
        return walk;
    }

    @Override
    public void moveToFront(Position<E> position) {
        Position<E> lastPosition = position;
        while (lastPosition != first()) {
            lastPosition = before(lastPosition);
        }
        if (lastPosition != position) {
            Node<E> lastNode = validate(addBefore(lastPosition, remove(position)));
            lastNode.setIndex(position.getIndex());
        }
    }

    @Override
    public void shuffle() {
        shuffle(random.nextInt(size() / 2));
    }

    @Override
    public void swap(Position<E> positionOne, Position<E> positionTwo) {
        Node<E> nodeOne = validate(positionOne);
        Node<E> nodeTwo = validate(positionTwo);
        E firstElement = positionOne.getElement();
        Integer firstElementIndex = positionOne.getIndex();
        E secondElement = positionTwo.getElement();
        Integer secondElementIndex = positionTwo.getIndex();

        nodeOne.setIndex(secondElementIndex);
        nodeOne.setElement(secondElement);
        nodeTwo.setIndex(firstElementIndex);
        nodeTwo.setElement(firstElement);
    }

    @Override
    public void swap(E elementOne, E elementTwo) {
        Position<E> positionOne = findPosition(elementOne);
        Position<E> positionTwo = findPosition(elementTwo);
        swap(positionOne, positionTwo);
    }

    @Override
    public int findIndexOfElementPosition(E element) {
        return findPosition(element).getIndex();
    }

    @Override
    public E next() throws IndexOutOfBoundsException {
        E element;
        if (isEmpty()) return null;
        if (currentPositionOfElement == null && !isEmpty()) currentPositionOfElement = header.getNextNode();
        if (currentPositionOfElement == last()) throw new IndexOutOfBoundsException("no more available records");
        currentPositionOfElement = currentPositionOfElement.getNextNode();
        element = currentPositionOfElement.getElement();
        return element;
    }

    @Override
    public E previous() throws IndexOutOfBoundsException {
        if (currentPositionOfElement == first()) throw new IndexOutOfBoundsException("no more available records");
        currentPositionOfElement = currentPositionOfElement.getPreviousNode();
        return currentPositionOfElement.getElement();
    }

    @Override
    public Iterator<Position<E>> iterator() {
        return new Iterator<Position<E>>() {
            private Position<E> current = first();
            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Position<E> next() {
                Position<E> position = current;
                current = after(position);
                return position;
            }
        };
    }

    private Node<E> validate(Position<E> position) {
        if (!(position instanceof PositionalLinkedList.Node<E> node)) throw new IllegalArgumentException("Invalid position");
        if (node.getNextNode() == null) throw new IllegalArgumentException("Position no longer exists");
        return node;
    }
    private Position<E> position(Node<E> node) {
        if (node == header || node == trailer) return null;
        return node;
    }
    private Position<E> addBetween(Integer index, E element, Node<E> predecessor, Node<E> successor) {
        Node<E> newNode = new Node<>(index, element, predecessor, successor);
        predecessor.setNextNode(newNode);
        successor.setPreviousNode(newNode);
        size++;
        return position(newNode);
    }
    private void shuffle(int numberOfTimes) {
        E element = null;
        Position<E> lastPosition = last();
        Position<E> foundPosition = null;
        if (size() % 2 == 1) element = remove(lastPosition);
        int n = 0;
        while (n < numberOfTimes) {
            List<Position<E>> firstList = new ArrayList<>();
            List<Position<E>> secondList = new ArrayList<>();

            Position<E> walk = first();
            int j = 0, k = size() / 2;
            while (j < k) {
                firstList.add(walk);
                walk = after(walk);
                j++;
            }
            while (k < size()) {
                secondList.add(walk);
                walk = after(walk);
                k++;
            }
            for (int i = 0; i < firstList.size(); i++) {
                swap(firstList.get(i), secondList.get(i));
            }
            foundPosition = firstList.get(random.nextInt(firstList.size()));
            n++;
        }
        if (element != null) {
            Node<E> lastNode = validate(addAfter(foundPosition, element));
            lastNode.setIndex(lastPosition.getIndex());
        }
    }
}
