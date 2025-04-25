/*
 * Copyright (C) 2024 John Burbridge
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class SingletonPatternsTest {

    @Test
    public void testEagerSingleton() {
        EagerSingleton instance1 = EagerSingleton.getInstance();
        EagerSingleton instance2 = EagerSingleton.getInstance();
        assertSame(instance1, instance2, "EagerSingleton instances should be the same");
    }

    @Test
    public void testStaticBlockSingleton() {
        StaticBlockSingleton instance1 = StaticBlockSingleton.getInstance();
        StaticBlockSingleton instance2 = StaticBlockSingleton.getInstance();
        assertSame(instance1, instance2, "StaticBlockSingleton instances should be the same");
    }

    @Test
    public void testLazySynchronizedSingleton() {
        LazySynchronizedSingleton instance1 = LazySynchronizedSingleton.getInstance();
        LazySynchronizedSingleton instance2 = LazySynchronizedSingleton.getInstance();
        assertSame(instance1, instance2, "LazySynchronizedSingleton instances should be the same");
    }

    @Test
    public void testDoubleCheckedSingleton() {
        DoubleCheckedSingleton instance1 = DoubleCheckedSingleton.getInstance();
        DoubleCheckedSingleton instance2 = DoubleCheckedSingleton.getInstance();
        assertSame(instance1, instance2, "DoubleCheckedSingleton instances should be the same");
    }

    @Test
    public void testBillPughSingleton() {
        BillPughSingleton instance1 = BillPughSingleton.getInstance();
        BillPughSingleton instance2 = BillPughSingleton.getInstance();
        assertSame(instance1, instance2, "BillPughSingleton instances should be the same");
    }

    @Test
    public void testEnumSingleton() {
        EnumSingleton instance1 = EnumSingleton.INSTANCE;
        EnumSingleton instance2 = EnumSingleton.INSTANCE;
        assertSame(instance1, instance2, "EnumSingleton instances should be the same");
    }
}
