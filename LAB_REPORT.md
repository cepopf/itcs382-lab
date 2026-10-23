# Lab Report, Code Quality and Design Quality

Repository URL: https://github.com/cepopf/itcs382-lab

## Tools

| Item | Version or name |
| --- | --- |
| JDK | Oracle JDK 24.0.2 (Maven runs on OpenJDK 27.0.1) |
| Maven | Apache Maven 3.10.0 |
| IDE | Visual Studio Code 1.142.0 |
| SonarQube for IDE | SonarQube for IDE (VS Code extension) 6.2.0 |
| AI tool and exact model | Gemini 3.6 Flash (review and bikeshare generation); Claude Code with Claude Opus 5.5 (help with fixes and report checking) |

## Task 1. SonarQube on the starter code

Commit of the starter, before any change: 52ef92c

Table 1. SonarQube issues in the starter code

| No. | Rule | File:line | Message |
| --- | --- | --- | --- |
| 1 | java:S106 | Main.java:9 | Replace this use of System.out by a logger. |
| 2 | java:S106 | Main.java:15 | Replace this use of System.out by a logger. |
| 3 | java:S106 | Main.java:17 | Replace this use of System.out by a logger. |
| 4 | java:S106 | Main.java:22 | Replace this use of System.out by a logger. |
| 5 | java:S2143 | ParcelService.java:1 | Use the "java.time" API for date and time. |
| 6 | java:S1128 | ParcelService.java:6 | Remove this unused import 'java.util.Date'. |
| 7 | java:S1068 | ParcelService.java:12 | Remove this unused "maxParcels" private field. |
| 8 | java:S106 | ParcelService.java:30 | Replace this use of System.out by a logger. |
| 9 | java:S4973 | ParcelService.java:42 | Strings and Boxed types should be compared using "equals()". |
| 10 | java:S106 | ParcelService.java:67 | Replace this use of System.out by a logger. |
| 11 | java:S106 | ParcelService.java:69 | Replace this use of System.out by a logger. |
| 12 | java:S2095 | ParcelService.java:76 | Use try-with-resources or close this "FileWriter" in a "finally" clause. |
| 13 | java:S108 | ParcelService.java:80 | Remove this block of code, fill it in, or add a comment explaining why it is empty. |

## Task 2. AI review of the starter code

| Item | Value |
| --- | --- |
| Date | 23 Oct 2026 |
| AI tool and exact model |Gemini 3.6 Flash |
| Commit reviewed |52ef92c |
| How the AI read the files (attached the files, opened the folder, or pasted the files) |attached the files |
| Output file in the reviews folder | reviews/parcelpoint-review.md|

Table 2. All findings on the starter code

| No. | File:line | Finding | Found by (SonarQube, AI or both) | SonarQube rule or course term | Correct? (yes or no, and why) |
| --- | --- | --- | --- | --- | --- |
| 1 | Main.java:9, 15, 17, 22; ParcelService.java:30, 67, 69 | System.out used instead of a logger | SonarQube | java:S106 | yes, but acceptable for a small console program |
| 2 | ParcelService.java:1 | Use the java.time API for date and time | SonarQube | java:S2143 | yes, java.util.Date is the old API (it was also unused) |
| 3 | Main.java:8 | Scanner is never closed | AI | Probable bugs | yes, resource leak |
| 4 | ParcelService.java:6 | Unused import java.util.Date | both | java:S1128 | yes, import is not used |
| 5 | ParcelService.java:12 | Unused field maxParcels | both | java:S1068 | yes, field is never referenced |
| 6 | ParcelService.java:42 | String comparison using == | both | java:S4973 | yes, should use .equals() |
| 7 | ParcelService.java:76 | FileWriter not closed | both | java:S2095 | yes, unclosed file stream |
| 8 | ParcelService.java:80-81 | Empty catch block | both | java:S108 | yes, exception is ignored |
| 9 | ParcelService.java:64-72 | Boolean flag forStaff in printParcels | AI | Control coupling / logical cohesion | yes, the flag selects which code runs |
| 10 | ParcelService.java:74-82 | ParcelService also writes the CSV file | AI | Weak (non-functional) cohesion | yes, mixes business and storage logic |
| 11 | ParcelService.java:20-22 | getLockers() returns the internal mutable list | AI | Coupling / Encapsulation | yes, callers can change the list |
| 12 | Main.java:22 | Main counts free lockers through service.getLockers() | AI | Coupling | yes, Main depends on Locker internals of the service |
| 13 | ParcelService.java:24 | storeParcel receives the whole Parcel object | AI | Stamp coupling | partly, passing a Parcel is a normal design here |
| 14 | Locker.java:5, Parcel.java:6, ParcelService.java:56-61 | Magic numbers 1, 2, 3 for size | AI | Connascence of convention | yes, size represented by magic ints |
| 15 | Parcel.java:13, ParcelService.java:28, 44, 68 | Magic strings "NEW", "STORED", "PICKED_UP" for status | AI | Connascence of convention | yes, status represented by magic strings |
| 16 | Parcel.java:7 | Field status is public | AI | Encapsulation | yes, status should be private |

2.1 Name two problems that only the AI review found.

AI found design problems like mixing file saving in ParcelService and using magic numbers 1, 2, 3 for sizes. SonarQube missed them because it only checks basic syntax, not design or class relationships.

2.2 Name one problem that only SonarQube found, or write none.

SonarQube found the use of System.out instead of a logger (java:S106, for example Main.java:9). The AI review did not report it.

2.3 Name one AI finding that was wrong or too vague, and explain why. If every finding was right, explain how you checked them.

The problems the AI described were real, but many of its line numbers were wrong. For example, it put the == string comparison at ParcelService.java:37, but it is at line 42, and it put the unclosed FileWriter at lines 68-71, but it is at line 76. I found this by checking each finding against the source in VS Code. The stamp coupling finding (storeParcel receives a whole Parcel) is also weak, because passing the Parcel object is a reasonable design here.

## Task 3. Fix and check

Table 3. What you did with each SonarQube issue in Table 1

| No. in Table 1 | Fixed or kept | Commit, or the reason for keeping it |
| --- | --- | --- |
| 1 | kept | System.out needed for console output in CLI lab |
| 2 | kept | System.out needed for console output in CLI lab |
| 3 | kept | System.out needed for console output in CLI lab |
| 4 | kept | System.out needed for console output in CLI lab |
| 5 | Fixed | 693b0ab |
| 6 | Fixed | 693b0ab |
| 7 | Fixed | 693b0ab |
| 8 | kept | System.out needed for console output in CLI lab |
| 9 | Fixed | 693b0ab |
| 10 | kept | System.out needed for console output in CLI lab |
| 11 | kept | System.out needed for console output in CLI lab |
| 12 | Fixed | 693b0ab |
| 13 | Fixed | 693b0ab |

Number of SonarQube issues that remain after your fixes: 8

The two design problems that you fixed

| Design problem (course term) | What you changed | Commit |
| --- | --- | --- |
| Control coupling (flag parameter) | Replaced printParcels(boolean forStaff) with printWaitingParcels() and printAllParcels(), and updated Main | 5f9b7a7 |
| Encapsulation | Made Parcel.status private and added getStatus()/setStatus() | 693b0ab |

Output of the program after your fixes, for the commands `store P1 6501 2`, `pickup P1 5` and `staff`, and the content of parcels.csv:

```text
store P1 6501 2
SMS to 6501: parcel P1 is in locker 2
Locker 2
pickup P1 5
Fee: 30.0
staff
P1 6501 PICKED_UP
Free lockers: 3

Content of parcels.csv:
P1,PICKED_UP
```

## Task 4. AI-generated poor and good designs

| Item | bikeshare-poor | bikeshare-good |
| --- | --- | --- |
| Date | 24 Oct 2026 | 24 Oct 2026 |
| AI tool and exact model | Gemini 3.6 Flash| Gemini 3.6 Flash|
| Prompt (B or C as given, or your changed prompt in full) | Prompt B| Prompt C|
| Builds with mvn package (yes or no) |yes |yes |
| Number of classes, interfaces, enums and records |1 class | 7 classes, 1 interface, 1 enum|
| SonarQube issues, total | 3| 0|
| SonarQube issues, by rule |java:S106, java:S1068 |none |
| AI review findings in Check 1 |2 findings |0 findings |
| AI review findings in Check 2 |5 findings | 0 findings|
| Review output file in the reviews folder |reviews/bikeshare-poor-review.md |reviews/bikeshare-good-review.md |

4.1 Did SonarQube report any cohesion or coupling problem in bikeshare-poor? Quote the rule and the message if it did.

No, SonarQube did not report any cohesion or coupling problems. It only flagged basic code smells like java:S106 (use logger instead of System.out) because SonarQube performs rule-based static analysis line-by-line, rather than analyzing architectural design or class relationships.

4.2 Did the AI review tell the two versions apart? Give one finding for bikeshare-poor that does not appear for bikeshare-good.

Yes, the AI review clearly distinguished between the two versions. For bikeshare-poor, the AI identified weak cohesion and control coupling (e.g., using boolean flags and putting menu, fee calculation, and file storage in a single class), whereas these design flaws were not found in bikeshare-good.

4.3 Which version would you rather extend with a new member type, such as a visitor who pays 4 baht per minute, and why?

I would prefer to extend bikeshare-good. Because member types are encapsulated in an enum/interface structure (MemberType), adding a new visitor type only requires adding a new enum constant with its rate, without touching or risking bugs in the core business logic.

## Task 5. Reflection

5.1 What does SonarQube check, and what does it not check? Answer with examples from this lab.

SonarQube checks code-level rules such as unused imports and fields, string comparison with ==, empty catch blocks and resource leaks (e.g., the unclosed FileWriter in ParcelService). However, it does not check high-level design quality, class cohesion, or coupling (e.g., it completely missed flag-driven control coupling and mixing CSV file I/O with business logic in ParcelService).

5.2 When should you not trust the AI review? Give an example from this lab.

You should not trust the AI review for exact locations or for complete coverage. In this lab, many of the AI's line numbers were wrong (e.g., the == comparison was reported at line 37 instead of line 42), and it did not report the System.out issues that SonarQube found. Every AI finding must be checked against the code.

5.3 What will you do differently in Project Phase 2 after this lab?

In Phase 2, I will use SonarQube continuously during development to catch resource leaks and syntax bugs early, while using AI reviews during refactoring phases to evaluate high-level architecture, class cohesion, encapsulation, and coupling between modules.

## AI usage declaration

| AI tool and exact model | Purpose | Files or parts that it produced or changed | How you checked its output |
| --- | --- | --- | --- |
| Gemini 3.6 Flash| Code Review & Design Generation| reviews/*.md, bikeshare-poor, bikeshare-good|Verified build status with mvn package, checked class structure and SonarQube issues in VS Code |
| Claude Code, Claude Opus 5.5 | Help with the design fixes and checking the report for mistakes | ParcelService.java, Main.java, Parcel.java (commits 693b0ab, 5f9b7a7), corrections in LAB_REPORT.md | Ran mvn package and the commands store P1 6501 2, pickup P1 5, staff, and compared the output and parcels.csv |
