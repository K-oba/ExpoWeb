'use strict';

describe('Controller Tests', function() {

    describe('Click Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockClick, MockStand, MockSubCategoria, MockExposicion;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockClick = jasmine.createSpy('MockClick');
            MockStand = jasmine.createSpy('MockStand');
            MockSubCategoria = jasmine.createSpy('MockSubCategoria');
            MockExposicion = jasmine.createSpy('MockExposicion');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Click': MockClick,
                'Stand': MockStand,
                'SubCategoria': MockSubCategoria,
                'Exposicion': MockExposicion
            };
            createController = function() {
                $injector.get('$controller')("ClickDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'expoCrApp:clickUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
