// Set up a collection to contain player information. On the server,
// it is backed by a MongoDB collection named "players."

Players = new Meteor.Collection("players");
CurrentLapData = new Meteor.Collection("currentLapData");

if (Meteor.isClient) {
  Template.leaderboard.players = function () {
    return Players.find({}, { sort: [["total_time_in_milliseconds", "asc"]] } );
  };

  Template.leaderboard.selected_name = function () {
    var player = Players.findOne(Session.get("selected_player"));
    return player && player.total_time;
  };

  Template.player.selected = function () {
    return Session.equals("selected_player", this._id) ? "selected" : '';
  };

  Template.player.events({
    'click': function () {
      Session.set("selected_player", this._id);
    }
  });

  Template.current_match.current_lap_data = function () {
    return CurrentLapData.find({}, { sort: [["name", "asc"]] });
  };

}

String.prototype.to_milliseconds = function() {
  minutes = parseInt(this.split(':')[0]);
  seconds = parseFloat(this.split(':')[1]);

  return minutes * 60 * 1000 + seconds * 1000
}

// On server startup, create some players if the database is empty.
if (Meteor.isServer) {
  Meteor.startup(function () {

  // Revoke clients rights to insert update or remove
  Meteor.default_server.method_handlers['/players/insert'] = function () {};
  Meteor.default_server.method_handlers['/players/update'] = function () {};
  Meteor.default_server.method_handlers['/players/remove'] = function () {};

    if (Players.find().count() === 0) {
      var names = ["Sven Kramer",
                   "Carl Verheijen",
                   "Chad Hedrick",
                   "Jochem Uytdehaage",
                   "Nikola Tesla",
                   "Gianni Romme"];
      var total_times = ["12:51.60",
                        "13:49.65",
                        "14:06.31",
                        "14:05.32",
                        "14:02.54",
                        "14:10.24"];
      for (var i = 0; i < names.length; i++)
        Players.insert({ name: names[i], total_time: total_times[i], total_time_in_milliseconds: total_times[i].to_milliseconds() });
    }

    if (CurrentLapData.find({}).count() === 0) {
      CurrentLapData.insert({ name: "S. Kramer", round: "1", lap_time: "33.5", total_time: "0:33.5", difference: "+0.4" });
      CurrentLapData.insert({ name: "I. Skobrev", round: "1", lap_time: "31.5", total_time: "0:31.5", difference: "" });
    };
  });
}
