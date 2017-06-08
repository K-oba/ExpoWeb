'use strict';

describe('Controller Tests', function() {

    describe('Stand Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockStand, MockUsuario, MockBrouchure, MockClick, MockBeacon;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockStand = jasmine.createSpy('MockStand');
            MockUsuario = jasmine.createSpy('MockUsuario');
            MockBrouchure = jasmine.createSpy('MockBrouchure');
            MockClick = jasmine.createSpy('MockClick');
            MockBeacon = jasmine.createSpy('MockBeacon');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Stand': MockStand,
                'Usuario': MockUsuario,
                'Brouchure': MockBrouchure,
                'Click': MockClick,
                'Beacon': MockBeacon
            };
            createController = function() {
                $injector.get('$controller')("StandDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'expoCrApp:standUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
