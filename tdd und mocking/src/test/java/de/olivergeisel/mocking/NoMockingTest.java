package de.olivergeisel.mocking;

import de.olivergeisel.support.LinkedList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NoMockingTest {

	LinkedList<String> list = new LinkedList<>();
	private StackWrapper<String> stack;

	@BeforeEach
	void setUp() {
		list.addAll("Hallo", "Welt", "SWT", "TDD", "JAVA");
		stack = new StackWrapper<>(list);
	}

	@AfterEach
	void tearDown() {
	}


	@Test
	@DisplayName("Init stack")
	void init_stack() {
		StackWrapper<String> newStack = new StackWrapper<>(list);
	}

	@Test
	@DisplayName("Throw exception if list is null")
	void throw_exception_if_list_is_null() {
		assertThrows(IllegalArgumentException.class, () -> new StackWrapper<>(null));
	}

	@Test
	@DisplayName("Size of stack should be equal to size of list")
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
		assertEquals(oldSize + 1, stack.size());
	}

	@Test
	@DisplayName("Pop one element from stack")
	void pop_one_element() {
		int oldSize = stack.size();
		var elem = stack.pop();
		assertNotNull(elem);
		assertEquals(oldSize - 1, stack.size());
	}

	@Test
	@DisplayName("Pop one element from empty stack and get null")
	void pop_one_element_from_empty_stack() {
		StackWrapper<String> newStack = new StackWrapper<>(List.of());

		var elem = newStack.pop();
		assertNull(elem);
		assertEquals(0, stack.size());
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
		StackWrapper<String> newStack = new StackWrapper<>(List.of());

		assertThrows(IllegalStateException.class, newStack::peek);
	}

}