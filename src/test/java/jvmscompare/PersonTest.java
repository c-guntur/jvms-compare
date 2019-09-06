package jvmscompare;

import jvmscompare.Person;
import org.junit.Assert;
import org.junit.Test;

/**
 * This is a set of unit tests to validate the code is working as expected.
 */
public class PersonTest
{
    @Test
    public void ageStatistics()
    {
        Assert.assertEquals(Person.NUMBER_OF_PEOPLE,
                            Person.getJDKPeople().stream().mapToInt(Person::getAge).summaryStatistics().getCount());
        Assert.assertEquals(Person.NUMBER_OF_PEOPLE,
                            Person.getECPeople().asLazy().collectInt(Person::getAge).summaryStatistics().getCount());
    }

    @Test
    public void weightStatisticsJDK()
    {
        Assert.assertEquals(Person.NUMBER_OF_PEOPLE,
                            Person.getJDKPeople().stream().mapToDouble(Person::getWeightInPounds).summaryStatistics().getCount());
        Assert.assertEquals(Person.NUMBER_OF_PEOPLE,
                            Person.getECPeople().asLazy().collectDouble(Person::getWeightInPounds).summaryStatistics().getCount());
    }

    @Test
    public void heightStatisticsJDK()
    {
        Assert.assertEquals(Person.NUMBER_OF_PEOPLE,
                            Person.getJDKPeople().stream().mapToDouble(Person::getHeightInInches).summaryStatistics().getCount());
        Assert.assertEquals(Person.NUMBER_OF_PEOPLE,
                            Person.getECPeople().asLazy().collectDouble(Person::getHeightInInches).summaryStatistics().getCount());
    }
}
