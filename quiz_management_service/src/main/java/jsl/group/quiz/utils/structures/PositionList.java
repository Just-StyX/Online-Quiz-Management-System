package jsl.group.quiz.utils.structures;

import java.util.Comparator;

public interface PositionList<E> extends Iterable<Position<E>> {
    int size();
    boolean isEmpty();
    Position<E> first();
    Position<E> last();
    Position<E> after(Position<E> position) throws IllegalArgumentException;
    Position<E> before(Position<E> position) throws IllegalArgumentException;
    Position<E> addFirst(E element);
    Position<E> addLast(E element);
    Position<E> addBefore(Position<E> position, E element) throws IllegalArgumentException;
    Position<E> addAfter(Position<E> position, E element) throws IllegalArgumentException;
    E set(Position<E> position, E element) throws IllegalArgumentException;
    E remove(Position<E> position) throws IllegalArgumentException;
    void sort(Comparator<E> comparator);
    int indexOf(Position<E> position);
    Position<E> findPosition(E element);
    boolean contains(E element);
    void clear();
    Position<E> positionAtIndex(int i);
    void moveToFront(Position<E> position);
    void shuffle();
    void swap(Position<E> positionOne, Position<E> positionTwo);
    void swap(E elementOne, E elementTwo);
    int findIndexOfElementPosition(E element);
    E next() throws IndexOutOfBoundsException;
    E previous() throws IndexOutOfBoundsException;
}
