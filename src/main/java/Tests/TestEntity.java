package Tests;

import Domain.Entity;
import org.testng.annotations.Test;

import static org.junit.Assert.assertEquals;

public class TestEntity {

        @Test
        public void testEntity()
        {
            class TestEntityImpl extends Entity {
                public TestEntityImpl(int id) {
                    super(id);
                }



            }

            // Instantierea clasei derivată
            TestEntityImpl testEntity = new TestEntityImpl(4);

            // Testarea metodelor
            assertEquals(4, testEntity.getId());

        }

    }

