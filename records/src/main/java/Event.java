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
import java.time.Instant;

public record Event(Instant timestamp, String name, String message) implements Comparable {
    @Override
    public int compareTo(Object other) {
        if (!(other instanceof Event otherEvent)) {
            throw new RuntimeException(String.format("%s other is not of type Event", other));
        }
        return this.timestamp.compareTo(otherEvent.timestamp);
    }
}
