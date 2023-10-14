package de.olivergeisel.mocking;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WithMockingTest {

	@Mock
	LinkedList<String> list;
	private StackWrapper<String> stack;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		when(list.size()).thenReturn(5);
		when(list.add(anyString())).thenReturn(true);
		when(list.get(0)).thenReturn("Hallo"); // list.add("Hallo");
		when(list.get(1)).thenReturn("Welt");  // list.add("Welt");
		when(list.get(2)).thenReturn("SWT");   // list.add("SWT");
		when(list.get(3)).thenReturn("TDD");   // list.add("TDD");
		when(list.get(4)).thenReturn("JAVA");  // list.add("JAVA");
		when(list.pop()).thenReturn("JAVA");
		when(list.remove(4)).thenReturn("JAVA");
		stack = new StackWrapper<>(list);
	}

	@AfterEach
	void tearDown() {
	}


	@Test
	@DisplayName("Init stack")
	void init_stack() {
		new StackWrapper<>(list);
	}

	@Test
	@DisplayName("Throw exception if list is null")
	void throw_exception_if_list_is_null() {
		assertThrows(IllegalArgumentException.class, () -> new StackWrapper<>(null));
	}

	@Test
	@DisplayName("Size of stack should be equal to 0 of list")
	void size_to_begin() {
		assertEquals(list.size(), stack.size());
	}

	@Test
	@DisplayName("Size of stack should be equal to 0 if list is empty")
	void size_to_begin_with_empty_list() {
		stack = new StackWrapper<>(List.of());
		assertEquals(0, stack.size());
	}

	@Test
	@DisplayName("Add element to stack")
	void add_element() {
		int oldSize = stack.size();
		stack.add("Neues Element");
		when(list.size()).thenReturn(6);
		assertEquals(oldSize + 1, stack.size());
	}

	@Test
	@DisplayName("Pop one element from stack")
	void pop_one_element() {
		int oldSize = stack.size();
		var elem = stack.pop();
		assertNotNull(elem);
		when(list.size()).thenReturn(4);
		assertEquals(oldSize - 1, stack.size());
	}

	@Test
	@DisplayName("Pop one element from empty stack and get null")
	void pop_one_element_from_empty_stack() {
		@SuppressWarnings("unchecked")
		LinkedList<String> newList = mock(LinkedList.class);
		StackWrapper<String> newStack = new StackWrapper<>(newList);
		when(newList.remove()).thenReturn(null);

		var elem = newStack.pop();
		assertNull(elem);
		assertEquals(0, newStack.size());
	}

	@Test
	@DisplayName("Pop one element from stack and then get null")
	void pop_one_element_from_stack() {
		@SuppressWarnings("unchecked")
		LinkedList<String> newList = mock(LinkedList.class);
		StackWrapper<String> newStack = new StackWrapper<>(newList);
		when(newList.remove(anyInt())).thenReturn("Welt").thenReturn(null);
		when(newList.size()).thenReturn(1).thenReturn(0, 0, 0, 0, 0, 0, 0);

		var elem1 = newStack.pop();
		assertNotNull(elem1);
		assertEquals(0, newStack.size());
		var elem2 = newStack.pop();
		assertNull(elem2);
		assertEquals(0, newStack.size());

		verify(newList, times(2)).remove(anyInt());
		verify(newList, times(4)).size();
	}

	@Test
	@DisplayName("Throw exception if null is added")
	void throw_exception_if_null_is_added() {
		assertThrows(IllegalArgumentException.class, () -> stack.push((String) null));
	}

	@Test
	@DisplayName("Throw exception if list with null is added")
	void throw_exception_if_list_with_null_is_added() {
		assertThrows(IllegalArgumentException.class, () -> stack.push((String[]) null));
	}

	@Test
	@DisplayName("Throw exception if peek is called on empty stack")
	void throw_exception_if_peek_is_called_on_empty_stack() {
		@SuppressWarnings("unchecked")
		LinkedList<String> newList = mock(LinkedList.class);
		StackWrapper<String> newStack = new StackWrapper<>(newList);
		when(newList.get(anyInt())).thenThrow(IllegalStateException.class);
		when(newList.size()).thenReturn(0);

		assertThrows(IllegalStateException.class, newStack::peek);
	}
}
