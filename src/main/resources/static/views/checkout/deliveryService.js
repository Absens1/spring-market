angular.module('app').service('deliveryService', function() {
  var deliveryTypeId;

  var setDeliveryTypeId = function(id) {
      deliveryTypeId = id;
  };

  var getDeliveryTypeId = function() {
      return deliveryTypeId;
  };

  return {
    setDeliveryTypeId: setDeliveryTypeId,
    getDeliveryTypeId: getDeliveryTypeId
  };
});