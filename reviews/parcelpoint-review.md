Files read: Locker.java, Main.java, Parcel.java, ParcelService.java

| No. | Check | Code location (file:line) | Finding | Severity | Suggested fix |
| --- | --- | --- | --- | --- | --- |
| 1 | Probable bugs | Main.java:7 | Scanner resource for standard input is never closed. | Should fix | Close Scanner or wrap it in a try-with-resources statement. |
| 2 | Probable bugs | ParcelService.java:37 | String comparison uses == instead of .equals() to check parcel.getCode() == code. | Must fix | Replace == with parcel.getCode().equals(code). |
| 3 | Probable bugs | ParcelService.java:68-71 | FileWriter resource is opened but never closed or flushed, which can cause data loss. | Must fix | Wrap FileWriter in a try-with-resources statement. |
| 4 | Probable bugs / Code smells | ParcelService.java:72-73 | Catch block for IOException is empty. | Should fix | Log or rethrow the caught exception instead of ignoring it. |
| 5 | Code smells | ParcelService.java:5 | Unused import java.util.Date. | Note | Remove the unused java.util.Date import statement. |
| 6 | Code smells | ParcelService.java:11 | Unused field maxParcels. | Note | Delete the unused maxParcels field. |
| 7 | Cohesion | ParcelService.java:58-65 | printParcels(boolean forStaff) has logical cohesion because it groups printing logic that behaves differently based on a flag. | Should fix | Split printParcels into two dedicated methods for public and staff printing. |
| 8 | Cohesion | ParcelService.java:67-74 | ParcelService class lacks functional cohesion by mixing domain/service logic with file I/O operations. | Should fix | Extract saveToFile into a dedicated repository or storage class. |
| 9 | Coupling | ParcelService.java:23 | getLockers() returns the raw mutable collection lockers, allowing external callers to modify internal state. | Should fix | Return an unmodifiable view or copy of the lockers collection. |
| 10 | Coupling | Main.java:21 | Main reaches through the lockers list and calls Locker::isFree directly (service.getLockers().stream()...). | Note | Delegate free locker counting directly to ParcelService. |
| 11 | Coupling | ParcelService.java:26-31 | Stamp coupling occurs as storeParcel(Parcel parcel) receives a full Parcel object to pass down and inspect properties. | Note | Pass only necessary fields or keep Parcel creation internal to the service. |
| 12 | Coupling | ParcelService.java:58 | Control coupling occurs via printParcels(boolean forStaff), where the boolean flag controls internal execution flow. | Should fix | Replace the flag parameter with separate explicit methods for each view. |
| 13 | Connascence | Locker.java:4, Parcel.java:5, ParcelService.java:49-55 | Connascence of convention occurs using magic integer constants (1, 2, 3) across classes to represent locker/parcel sizes. | Should fix | Replace integer size codes with an enum Size { SMALL, MEDIUM, LARGE }. |
| 14 | Connascence | Parcel.java:6, ParcelService.java:29,39,61 | Connascence of convention occurs using magic strings ("NEW", "STORED", "PICKED_UP") to manage status values across classes. | Should fix | Replace status string literals with a Status enum. |
| 15 | Encapsulation | Parcel.java:6 | Field status is declared public instead of private. | Must fix | Make status private and expose getter/setter methods. |

### Summary of findings

- **Check 1 (Code quality):** 6 findings
- **Check 2 (Cohesion, coupling and connascence):** 9 findings