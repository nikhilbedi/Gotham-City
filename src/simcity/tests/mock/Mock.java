package simcity.tests.mock;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * This is the base class for all mocks.
 *
 * @author Nikhil Bedi
 *
 */
public class Mock {
	protected String name;

	public Mock(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String toString() {
		return this.getClass().getName() + ": " + name;
	}
}
