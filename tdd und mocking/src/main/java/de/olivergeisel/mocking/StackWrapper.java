package de.olivergeisel.mocking;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * A Wrapper to handle a list as stack. Takes a list and override the {@link Collection} interface to work
 * like "LIFO" principle.
 *
 * @param <T> Type of Element
 */
public class StackWrapper<T> implements Collection<T> {

	private final List<T> list;

	/**
	 * Created a new Wrapper for a list.
	 *
	 * @param list List to be wrapped
	 */
	public StackWrapper(List<T> list) {
		if (list == null) throw new IllegalArgumentException("List must not be null");
		this.list = list;
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public boolean contains(Object o) {
		return list.contains(o);
	}

	@Override
	public Iterator<T> iterator() {
		return list.iterator();
	}

	@Override
	public void forEach(Consumer<? super T> action) {
		list.forEach(action);
	}

	@Override
	public Object[] toArray() {
		return list.toArray();
	}

	@Override
	public <T1> T1[] toArray(T1[] a) {
		if (a.length < size()) {
			for (int i = size(); i > 0; --i) {
				a[size() - i] = (T1) list.get(i);
			}
			return a;
		}
		List<T> newList = new ArrayList<>();
		Collections.copy(list, newList);
		Collections.reverse(newList);
		return (T1[]) newList.toArray();
	}

	@Override
	public <T1> T1[] toArray(IntFunction<T1[]> generator) {
		return list.toArray(generator);
	}

	public boolean add(T element) {
		return list.add(element);
	}

	@Override
	public boolean remove(Object o) {
		return list.remove(o);
	}

	public T pop() {
		return list.remove(list.size() - 1);
	}

	public T[] pop(int num) {
		var back = list.subList(list.size() - num - 1, list.size() - 1);
		return (T[]) back.toArray();
	}

	public void push(T element) {
		if (element == null) throw new IllegalArgumentException("Element must not be null");
		list.add(element);
	}

	public void push(T[] elements) {
		if (elements == null) throw new IllegalArgumentException("Elements must not be null");
		list.addAll(Arrays.asList(elements));
	}

	public T peek() {
		return list.get(list.size() - 1);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return new HashSet<>(list).containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		return list.retainAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return list.retainAll(c);
	}

	@Override
	public boolean removeIf(Predicate<? super T> filter) {
		return list.removeIf(filter);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return list.retainAll(c);
	}

	@Override
	public void clear() {
		list.clear();
	}

	@Override
	public Spliterator<T> spliterator() {
		return Collection.super.spliterator();
	}

	@Override
	public Stream<T> stream() {
		return list.stream();
	}

	@Override
	public Stream<T> parallelStream() {
		return list.parallelStream();
	}

	//region setter/getter
	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}
//endregion

	@Override
	public boolean equals(Object o) {
		return list.equals(o);
	}

	@Override
	public int hashCode() {
		return list.hashCode();
	}

}
