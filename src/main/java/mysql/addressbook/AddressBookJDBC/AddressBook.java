package mysql.addressbook.AddressBookJDBC;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Address Book 
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import model.Person;

public class AddressBook {

	private static Scanner scanner = new Scanner(System.in);
	private ArrayList<Person> personList = null;
	private Map<String, ArrayList<Person>> detail = new HashMap<>();
	public String city;

	/**
	 * Display welcome message
	 */
	private void displayWelcome() {
		System.out.println("Welcome to address book");
	}

	/**
	 * UC2 This method adds object person and its fields to ArrayList
	 */
	private void add() {

		System.out.println("Add details in city you want?");
		city = scanner.next();
		Person person = new Person();
		System.out.println("First Name :");
		person.setFirstName(scanner.next());
		System.out.println("Last name : ");
		person.setLastName(scanner.next());
		System.out.println("Address :");
		person.setAddress(scanner.next());
		System.out.println("City :");
		person.setCity(scanner.next());
		System.out.println("State :");
		person.setState(scanner.next());
		System.out.println("Zip :");
		person.setZip(scanner.next());
		System.out.println("Phone :");
		person.setPhone(scanner.next());
		System.out.println("Email :");
		person.setEmail(scanner.next());

		if (detail.containsKey(city)) {
			detail.get(city).add(person);
		} else {
			personList = new ArrayList<>();
			personList.add(person);
			detail.put(city, personList);
		}

	}

	/**
	 * UC3 method finds person according to first name
	 */
	private void edit() {
		System.out.println("Enter the city to which u want to edit person ");
		city = scanner.next();
		String enteredName;
		System.out.println("Enter First name of contact to edit it ");
		enteredName = scanner.next();
		for (int i = 0; i < detail.get(city).size(); i++) {
			if (detail.get(city).get(i).getFirstName().equals(enteredName)) {
				int check = 0;
				System.out.println("Person found , what do you want to edit ?");
				System.out.println(
						"Enter\n1.First Name\n2.Last Name\n3.Address\n4.city\n5.State\n6.Zip\n7.Phone\n8.Email");
				check = scanner.nextInt();
				switch (check) {
				case 1:
					System.out.println("Enter new first name");
					detail.get(city).get(i).setFirstName(scanner.next());
					break;
				case 2:
					System.out.println("Enter new last name");
					detail.get(city).get(i).setLastName(scanner.next());
					break;
				case 3:
					System.out.println("Enter new Address");
					detail.get(city).get(i).setAddress(scanner.next());
					break;
				case 4:
					System.out.println("Enter new city");
					detail.get(city).get(i).setCity(scanner.next());
					break;
				case 5:
					System.out.println("Enter new state");
					detail.get(city).get(i).setState(scanner.next());
					break;
				case 6:
					System.out.println("Enter new zip");
					detail.get(city).get(i).setZip(scanner.next());
					break;
				case 7:
					System.out.println("Enter new phone number");
					detail.get(city).get(i).setPhone(scanner.next());
					break;
				case 8:
					System.out.println("Enter new email");
					detail.get(city).get(i).setEmail(scanner.next());
					break;
				default:
					System.out.println("Invalid Entry");

				}
			}
		}

	}

	/**
	 * UC4 removes person from list according to first name.
	 */
	private void delete() {
		System.out.println("Enter First name  to delete  ");
		String enteredName = scanner.next();
		for (int i = 0; i < detail.get(city).size(); i++) {
			if (detail.get(city).get(i).getFirstName().equals(enteredName))
				detail.get(city).remove(i);
		}
		System.out.println("Person removed from Address book");
	}

	/**
	 * Displays on console
	 */
	private void show() {
		System.out.println(detail);
	}

	/**
	 * UC8 : Ability to search person across all cities and Name of cities are
	 * stored as key of Hashmap loop iterate for all keys stream to search target
	 * person
	 */
	private void search() {
		System.out.println("Enter name to search across all books");
		String searchKey = scanner.next();
		for (String key : detail.keySet()) {
			detail.get(key).stream().filter(personList1 -> personList1.getFirstName().equals(searchKey))
					.collect(Collectors.toList()).forEach(Person -> System.out.println(Person.toString()));
		}
	}

	/**
	 * UC9 : Ability to view persons by city Gets city name from user If Hashmap
	 * contains that city as key it will display all contacts in list using stream..
	 * Use of Atomic integer is to count number of contacts.
	 */
	private void searchBycity() {
		System.out.println("Enter city name to display all contacts");
		String searchKey = scanner.next();
		AtomicInteger count = new AtomicInteger(0);
		if (detail.containsKey(searchKey)) {
			detail.get(searchKey).stream().forEach(Person -> {
				System.out.println(Person.toString());
				count.incrementAndGet();
			});
			System.out.println("Number of contacts : " + count.get());
		} else {
			System.out.println("City not found");
		}

	}

	/**
	 * UC11 Sort by First name
	 */
	private void sortByFirstName() {
		for (String key : detail.keySet()) {
			detail.get(key).stream().sorted(Comparator.comparing(Person::getFirstName)).collect(Collectors.toList())
					.forEach(Person -> System.out.println(Person.toString()));
		}
	}

	/**
	 * UC12 Sort by city name
	 */
	private void sortByCity() {
		for (String key : detail.keySet()) {
			detail.get(key).stream().sorted(Comparator.comparing(Person::getCity)).collect(Collectors.toList())
					.forEach(Person -> System.out.println(Person.toString()));
		}
	}

	/**
	 * UC13: File IO Exception
	 * @throws IOException 
	 */
	public void writeToFile() throws IOException {
		FileOutputStream writeData = new FileOutputStream("BookDetail.txt");
		ObjectOutputStream writeStream = new ObjectOutputStream(writeData);
		try {
		
			writeStream.writeObject(detail);
			writeStream.flush();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			writeStream.close();
			
		}
		
	}
	

	/**
	 * Reads objects from file
	 * Prints them on console
	 * @throws IOException 
	 */
	public void readFromFile() throws IOException {
		FileInputStream readData = new FileInputStream("BookDetail.txt");
		ObjectInputStream readStream = new ObjectInputStream(readData);
		try {
			Map<String,ArrayList<Person>> bookdetail = (Map<String, ArrayList<Person>>) readStream.readObject();
			System.out.println(bookdetail);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			readStream.close();
		}
	}
	
	/**
	 * UC 14
	 *  Read the Address Book with Persons Contact
     * with CSV File
	 */
	public void readCsv() {
		try (CSVReader reader = new CSVReader(new FileReader("contacts.csv"));)
		{
			String[] nextLine;
			while((nextLine = reader.readNext()) != null) {
				if(nextLine != null) {
					System.out.println(Arrays.toString(nextLine));
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Read complete");
	}
	
	/**
	 * 
	 * @throws CsvDataTypeMismatchException
	 * @throws CsvRequiredFieldEmptyException
	 */
	@SuppressWarnings("unchecked")
	public void writeToCsv() throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
		try(FileWriter writer = new FileWriter("Bookcontacts.csv"))
		{
			@SuppressWarnings("rawtypes")
			ColumnPositionMappingStrategy mappingStrategy=
                    new ColumnPositionMappingStrategy();
			
			mappingStrategy.setType(Person.class);
			String[] columns = new String[] {"FirstName","LastName","Address",
					"city","state","zip","phone","email"};
			mappingStrategy.setColumnMapping(columns);
			
			@SuppressWarnings("rawtypes")
			StatefulBeanToCsvBuilder<Person> builder = 
					new StatefulBeanToCsvBuilder(writer); 
			
			@SuppressWarnings("rawtypes")
			StatefulBeanToCsv beanWriter = 
			          builder.withMappingStrategy(mappingStrategy).build();
			
			beanWriter.write(personList);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void convertToJson() {
		String result="";
		try{

			ObjectMapper mapper = new ObjectMapper();
			result = mapper.writeValueAsString(personList);
			for(Person person : personList) {
				mapper.writeValue(new File("AddressDetails.json"), person);
			}
			
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JacksonException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(result);
     }
	
	/*
	 * UC16
	 * Ability to DB Create connection
	 */
	private Connection getConnection() throws SQLException {
			String jdbcURL = "jdbc:mysql://localhost:3306/addressBook_service?useSSL=false";
			String userName = "root";
			String password = "root";
			Connection con;
			System.out.println("Connecting to database:" + jdbcURL);
			con = DriverManager.getConnection(jdbcURL, userName, password);
			System.out.println("Connection is successful!!!!" + con);
			return con;
		}
	
	public static void main(String[] args) throws IOException {
		AddressBook runner = new AddressBook();
		runner.displayWelcome();

		boolean isExit = false;
		while (!isExit) {
			System.out.println(
					"Enter options\n1.Add\n2.Edit\n3.Delete\n4.Show\n5.Search\n6.ShowCity\n7.SortByName\n8.SortByCity\n9.Exit");
			int userInput = scanner.nextInt();
			switch (userInput) {
			case 1:
				runner.add();
				break;
			case 2:
				runner.edit();
				break;
			case 3:
				runner.delete();
				break;
			case 4:
				runner.show();
				break;
			case 5:
				runner.search();
				break;
			case 6:
				runner.searchBycity();
				break;
			case 7:
				runner.sortByFirstName();
				break;
			case 8:
				runner.sortByCity();
				break;
			case 9 :
				runner.writeToFile();
				break;
			case 10 :
				runner.readFromFile();
				break;
			case 11:
				isExit = true;
				break;
			default:
				System.out.println("Invalid input");
			}
		}
	}

}

}
