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
public class LazySynchronizedSingleton {
    private static LazySynchronizedSingleton instance;

    private LazySynchronizedSingleton() {
        System.out.println("LazySynchronizedSingleton: Instance created");
    }

    public static synchronized LazySynchronizedSingleton getInstance() {
        if (instance == null) {
            instance = new LazySynchronizedSingleton();
        }
        return instance;
    }

    public void showMessage() {
        System.out.println("LazySynchronizedSingleton: This is a singleton instance");
    }
}
