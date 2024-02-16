import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;

/**
 * This class represent the main method of parsing and running the algorithms.
 * Please note that the "input.txt" should be located in the same directory as the program.
 */
public class Ex1 {

	public static void main(String[] args) throws IOException {
		Node node = new Node("", null, new FileParser("input.txt").parse());
		Date d1 = new Date();
		String result = Algorithms.run(node);
		Date d2 = new Date();
		if (Algorithms.with_time)
			result += "\n" + (d2.getTime() - d1.getTime()) / 1000.0 + " seconds";
		BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
		writer.write(result);
		writer.close();
	}
}
