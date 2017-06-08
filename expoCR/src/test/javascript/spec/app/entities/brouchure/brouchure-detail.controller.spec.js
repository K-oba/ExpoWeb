'use strict';

describe('Controller Tests', function() {

    describe('Brouchure Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockBrouchure, MockStand, MockSubCategoria;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockBrouchure = jasmine.createSpy('MockBrouchure');
            MockStand = jasmine.createSpy('MockStand');
            MockSubCategoria = jasmine.createSpy('MockSubCategoria');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Brouchure': MockBrouchure,
                'Stand': MockStand,
                'SubCategoria': MockSubCategoria
            };
            createController = function() {
                $injector.get('$controller')("BrouchureDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'expoCrApp:brouchureUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
