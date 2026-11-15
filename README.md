# A1-Ziliang-Lu-25259146 — PROG2004 Assessment 1

Health appointment system, implemented in Java with object-oriented design
concepts from Modules 1–3 of PROG2004 Object Oriented Programming.

There is no user interface, no menu, no login and no data persistence: the
project is a set of collaborating classes that model the domain, plus a
scripted demonstration in `main()` that exercises their behaviour and prints
the results to the console.

## Structure

```
A1-Ziliang-Lu-25259146/
├── src/
│   ├── AssignmentOne.java         entry point: main() and the demonstration
│   ├── HealthProfessional.java    abstract parent of every professional type
│   ├── Prescribable.java          capability: may prescribe medication
│   ├── GeneralPractitioner.java   a doctor, adds a consulting room
│   ├── Specialist.java            a doctor, adds an area of medicine
│   ├── Surgeon.java               a doctor, adds an operating theatre
│   ├── Nurse.java                 adds a ward, takes blood samples
│   ├── Patient.java               a patient: name and mobile number
│   ├── TimeSlot.java              one position in the clinic timetable
│   ├── Appointment.java           patient + professional + time slot
│   ├── AppointmentManager.java    booking, filtering, sorting, cancelling
│   └── ClinicConsole.java         interactive front end: login, book, cancel
└── README.md
```

| Area | Status |
|---|---|
| Health professional model | done — `HealthProfessional` |
| Health professional types | done — four types plus the `Prescribable` capability |
| Health professional objects | done — five doctors displayed together in `main()` |
| Appointment model | done — `Patient`, `TimeSlot` and `Appointment` with `Comparable` |
| Appointment management | done — booking, filtering, sorting and cancelling |
| End-to-end demonstration | done — `main()` shows the normal and the error cases |
| Interactive front end | done — `ClinicConsole`, started with the argument `console` |

## Domain constraints

- A health professional is identified by a numeric ID and a name, and works a
  fixed set of weekdays.
- Bookings are for the current day only.
- The clinic schedule is fixed: 08:00–16:00 in 30-minute slots.
- A health professional can never be double-booked into the same time slot.

## Design notes

The design follows the patterns from the Modules 1–3 tutorials:

- **Abstraction** — `HealthProfessional` is abstract, so a professional with no
  specialty cannot be created, and every subclass is forced to answer
  `getRole()`. `Prescribable` abstracts the prescribing capability on its own,
  which inheritance alone cannot express.
- **Encapsulation** — all fields are private and only getters are exposed. The
  constructor is the single place where data is set, and it is responsible for
  keeping the object valid, so external code can read a professional's details
  but can never put one into an invalid state.
- **Inheritance** — the four concrete types extend `HealthProfessional`, call
  `super(...)` to set up the shared fields, and reuse the shared behaviour
  instead of copying it.
- **Polymorphism** — the demonstration keeps every professional in a single
  `ArrayList<HealthProfessional>` and prints it in one loop; each object runs
  its own overridden `toString()`, which in turn calls its own `getRole()`.
- **Constructor overloading** — every type offers a shorter constructor that
  delegates with `this(...)` to the full one and supplies sensible defaults for
  the working days, stored as `private static final` constants.
- **Composition (has-a)** — an `Appointment` has a `Patient`, a
  `HealthProfessional` and a `TimeSlot` rather than copying their fields, so a
  change to a professional's room is visible everywhere at once. Its
  `toString()` calls theirs instead of re-formatting them.
- **`Comparable`** — `Appointment` and `TimeSlot` order themselves, so
  `Collections.sort(...)` sorts a day of bookings, and a filtered subset, with
  no comparison logic in the caller.
- **`equals` and `hashCode`** — two professionals are the same person when their
  identifiers match, and two appointments are the same booking slot when the
  professional and the time match. That second definition is exactly the rule
  the clinic forbids, so a double booking is detected by one `contains(...)`
  call.
- **Capability interface with `instanceof`** — the demonstration asks each
  professional whether it is `Prescribable` before prescribing, which is how one
  loop serves four types while the nurse correctly receives no prescription.
- **Gender and department** — every professional stores a gender and answers
  `getDepartment()`, so the console can show the same fields for all four types
  while each subclass supplies its own department.
- **Login kept out of the model** — `ClinicConsole` asks for the patient name and
  the phone number that works like a password; `Patient` only stores data. The
  check therefore lives in the console, and no domain class knows that a user
  interface exists.

## Code style

One style is used everywhere; it never varies between files:

- opening braces stay on the same line as the declaration (K&R)
- four spaces per indent level, never tabs
- every class and every method carries a Javadoc comment, because the
  assessment asks for Javadoc documentation
- fields and constants carry one short `//` comment at the end of the
  declaration line, for example `private String identifier;   // digits only`
- one blank line between members; files end with exactly one newline
- lines never exceed 100 columns
- identifiers are written in full words, never abbreviated (`identifier`,
  not `id`; `working days`, not `wd`)
- method names begin with a verb (`createHealthProfessionals`,
  `buildWorkingDays`, `displayHealthProfessionals`, `describeWorkingDays`)
- fields are `private` and camelCase; constants are `UPPER_CASE`

## Build and run

Compile and run from the project root with JDK 26:

```
javac -d out src/*.java
java -cp out AssignmentOne
```

## Progress

Development history is tracked with regular commits in this repository, and
time spent on the project is recorded with WakaTime.

## 如何运行这个程序（分步说明）

整个流程共五步。所有命令都在"项目文件夹对应的终端"里执行，顺序不能颠倒。

```bash
# ---------------------------------------------------------------------------
# 第 1 步：在 Visual Studio Code 里打开项目文件夹
#   文件 > 打开文件夹 > 选择 A1-Ziliang-Lu-25259146
#   左侧资源管理器最顶上一行必须显示这个文件夹名，src/ 里应当有 12 个 .java 文件。
#   如果打开的是上一层目录（例如 "Object Oriented Programming"），编译照样能过，
#   但"找不到文件"这类错误，绝大多数都是文件夹打开错了。
# ---------------------------------------------------------------------------

# ---------------------------------------------------------------------------
# 第 2 步：打开内置终端
#   Ctrl + `（反引号，Esc 键下面那个键）
#   提示符末尾必须是项目文件夹名，例如：
#   PS C:\...\A1-Ziliang-Lu-25259146>
#   如果不是，先切换到项目目录：
cd "C:\Users\23961\Desktop\外方作业\Object Oriented Programming\A1-Ziliang-Lu-25259146"
# ---------------------------------------------------------------------------

# ---------------------------------------------------------------------------
# 第 3 步：确认 JDK 可用
javac -version
# 期望输出：javac 26.0.2.1
# 如果提示"无法将 javac 识别为..."，说明终端里没有 JDK：关掉终端重开，
# 或用 VS Code 的"重新加载窗口"，仍不行则要先修好 PATH 再往下走。
# ---------------------------------------------------------------------------

# ---------------------------------------------------------------------------
# 第 4 步：把全部源文件编译到 out 文件夹
javac -d out src/*.java
# 期望输出：什么都不显示。没有消息就是成功。
# 编译完成后会多出 out/ 文件夹，里面有 12 个 .class 文件。它是编译产物：
# 已被 .gitignore 排除，不会进仓库，也不会进提交的 zip 包。
# 想删随时删，下次编译会自动重新生成。
# ---------------------------------------------------------------------------

# ---------------------------------------------------------------------------
# 第 5 步：运行演示程序
java -cp out AssignmentOne
# 期望输出：共 227 行，开头是
#   PROG2004 Assessment 1 - Health appointment system
# 结尾是
#   End of demonstration.
# 输出按作业的六个部分分节展示，顺序如下：
#   健康从业者详情            Part 3
#   从业者简介                多态（同一方法四种说法）
#   每人能做什么              Part 2（开药 / 手术 / 抽血）
#   预约挂号                  Part 5
#   全部预约（按时间排序）     Part 4 + Part 5
#   按医生编号筛选            1001
#   按患者手机号筛选          0412345678
#   取消预约（三种方式）      按医生+时间、按手机号、再取消一次
#   操作失败的警告            重复预约、非法时段、缺少医生
#   演示结束
# ---------------------------------------------------------------------------

# ---------------------------------------------------------------------------
# 可选：把输出保存成文件，方便慢慢看
java -cp out AssignmentOne > output.txt
# output.txt 不会被提交；用完可以删掉。
# ---------------------------------------------------------------------------

# ---------------------------------------------------------------------------
# 可选：进入交互式挂号控制台
java -cp out AssignmentOne console
# 脚本演示跑完之后进入交互界面，流程如下：
#
#   第 1 步 登录
#     屏幕上列出五位患者的名字（只显示姓名，不显示手机号），选一个编号，
#     再输入手机号当密码。手机号不对会提示：
#         Name or phone number do not match.
#     名单里没有自己时选 0，可以现场注册：输入姓名和手机号即可，
#     注册成功后自动登录。手机号已存在时会提示：
#         That phone number is already registered.
#
#   第 2 步 登录后的菜单
#     1  挂号
#     2  我的预约
#     3  查看全部预约
#     9  退出登录
#     0  退出程序
#
#   第 3 步 挂号（先选医生，再选时间段）
#     选择医生时，每位医生都会显示：姓名、性别、类型、科室、今日预约数。
#     确认选择后显示这位医生的完整档案（含简介）。
#     然后列出这位医生的全部时间段，已被预约的时段标为 already booked，
#     选中它会被拒绝：
#         That time is already booked for Alice Brown.
#
#   第 4 步 我的预约（保留或取消）
#     列出当前患者自己的预约，选中一条后显示详情，再选择：
#         1  keep this appointment
#         2  cancel this appointment
#     取消成功后回到列表，可以看到这条预约已经消失。
#
# 不带 console 参数时（也就是第 5 步）只有脚本演示，没有任何菜单。
# ---------------------------------------------------------------------------

# ---------------------------------------------------------------------------
# 注意：只要改了任意一个 .java 文件，都必须重新执行第 4 步再执行第 5 步。
# 程序运行的永远是编译出来的 .class 文件，而不是源文件。
# ---------------------------------------------------------------------------
```

### 如果运行出错了

| 报错信息 | 原因 | 解决办法 |
|---|---|---|
| `javac` 无法识别 | 终端的 PATH 里没有 JDK | 关掉终端重开，或修复 PATH |
| `Could not find or load main class AssignmentOne` | 跳过了第 4 步，或 `out/` 被删了 | 重新执行第 4 步 |
| `src/*.java: no such file or directory` | 终端不在项目文件夹里 | 执行第 2 步里的 `cd` 命令 |
| 输出只显示了一半 | 终端缓冲区被截断，不是程序出错 | 用第 5 步的可选命令输出到 `output.txt` 再看 |
