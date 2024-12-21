Model:

-Athlete 

-Achievement 

-Coach

-BookCoach

-TeammateRequest

Service: 

-AthleteService

-BookCoachService

-CoachService

Controller: 

-AthleteController

-BookCoachController

-CoachController

Repository:

-AchievemntReopsitory

-AthleteRepository

-BookCoachRepository

-CoachRepository

-TeammateRequestRepository

DTO:

-AchievemntOutDTO

-AthleteOutDTO

-CoachAthleteOutDTO

-BookCoachOutDTO

-CoachOutDTO

-TeammateRequestOutDTO


Extra End-points:

-requestCoachBooking→ athleteController → POST 

-acceptBookingRequest/rejectBookingRequest → coachController → PUT

-getAllAcceptedBookings → bookCoachController → GET

-addAchievement → athleteController → POST

-getAthleteAchievements → athleteController → GET

-findSameSportAndCityAthletes → athleteController → GET

-sendTeammateRequest → athleteController → POST

-acceptSponsorship/rejectSponsorShip → athleteController → PUT

-getAllCoachAthletes → coachController → GET

-respondToTeammateRequest → athleteController → PUT 
