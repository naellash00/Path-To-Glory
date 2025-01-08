# Path To Glory

**System Description**

Path to Glory is a platform designed to support Saudi athletes and aspiring individuals on their journey to the Olympics. This system facilitates connections with arenas that provide essential services, sponsors who can fund their ambitions, and fellow athletes in the same field, fostering collaboration and growth within the sports community

### Features

- **Athlete and Coach Management**: Comprehensive profiles for athletes and coaches, including age, city, and specialization.
- **Session Booking**: Athletes can book coaching sessions directly with coaches.
- **Teammate Requests**: Facilitates connection and collaboration between athletes through teammate requests.
- **Achievement Tracking**: Allows athletes to record and showcase their achievements.
- **Sponsorships**: Enables athletes to link with sponsors to support their journey.


---

## My Work on the Project

1. **Coach Management**: Implementation of the coach model, CRUD operations, and endpoints for retrieving coaches and their athletes.
2.  **Athlete Management**: Implementation of the coach model, CRUD operations, and endpoints for retrieving coaches and their athletes.
3. **Teammate Request Feature**: Developed logic for accepting or rejecting teammate requests, including validation and status updates.
4. **Athlete Profile and Booking System**: Built athlete model with CRUD functionality, and implemented booking relationships with coaches.
5. **Achievement Tracking**: Created functionality for athletes to track and manage their achievements.

### Extra Endpoints

1. **GET** `/coaches` - Retrieve all coaches. *(CoachController)*
2. **GET** `/coach/{coach_id}/athletes` - Retrieve athletes linked to a specific coach. *(CoachController)*
3. **POST** `/teammate-request` - Create a teammate request. *(TeammateRequestController)*
4. **PUT** `/teammate-request/{request_id}/accept` - Accept a teammate request. *(TeammateRequestController)*
5. **PUT** `/teammate-request/{request_id}/reject` - Reject a teammate request. *(TeammateRequestController)*
6. **POST** `/achievements` - Add a new achievement for an athlete. *(AchievementController)*
7. **GET** `/athlete/{athlete_id}/achievements` - Retrieve achievements of an athlete. *(AchievementController)*

---

### Controllers:

1. **CoachController**
2. **AthleteController**
3. **TeammateRequestController**
4. **AchievementController**

### DTOs:

1. **AthleteOutDTO**
2. **CoachOutDTO**
3. **TeammateRequestDTO**
4. **AchievementOutDTO**

### Models:

1. **Athlete**
2. **Coach**
3. **TeammateRequest**
4. **Achievement**
5. **Sponsorship**

### Repositories:

1. **AthleteRepository**
2. **CoachRepository**
3. **TeammateRequestRepository**
4. **AchievementRepository**

### Services:

1. **CoachService**
2. **AthleteService**
3. **TeammateRequestService**
4. **AchievementService**

