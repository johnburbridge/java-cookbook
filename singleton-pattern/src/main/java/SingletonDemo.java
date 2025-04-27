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
public class SingletonDemo {
    public static void main(String[] args) {
        System.out.println("Singleton Pattern Demo\n");

        // Eager Initialization
        System.out.println("\n--- Eager Initialization ---");
        EagerSingleton eagerSingleton = EagerSingleton.getInstance();
        eagerSingleton.showMessage();

        // Static Block Initialization
        System.out.println("\n--- Static Block Initialization ---");
        StaticBlockSingleton staticBlockSingleton = StaticBlockSingleton.getInstance();
        staticBlockSingleton.showMessage();

        // Lazy Initialization with synchronized method
        System.out.println("\n--- Lazy Initialization with synchronized method ---");
        LazySynchronizedSingleton lazySingleton = LazySynchronizedSingleton.getInstance();
        lazySingleton.showMessage();

        // Double-Checked Locking
        System.out.println("\n--- Double-Checked Locking ---");
        DoubleCheckedSingleton doubleCheckedSingleton = DoubleCheckedSingleton.getInstance();
        doubleCheckedSingleton.showMessage();

        // Bill Pugh Singleton (Initialization-on-demand Holder Idiom)
        System.out.println("\n--- Bill Pugh Singleton ---");
        BillPughSingleton billPughSingleton = BillPughSingleton.getInstance();
        billPughSingleton.showMessage();

        // Enum Singleton
        System.out.println("\n--- Enum Singleton ---");
        EnumSingleton enumSingleton = EnumSingleton.INSTANCE;
        enumSingleton.showMessage();

        // Verify singleton behavior by getting a second instance
        System.out.println("\n--- Verifying Singleton Behavior ---");
        EagerSingleton eagerSingleton2 = EagerSingleton.getInstance();
        System.out.println("Same instance? " + (eagerSingleton == eagerSingleton2));
    }
}
