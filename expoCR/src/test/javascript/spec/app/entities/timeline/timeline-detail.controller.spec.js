'use strict';

describe('Controller Tests', function() {

    describe('Timeline Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTimeline, MockExposicion, MockPost, MockUsuario;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTimeline = jasmine.createSpy('MockTimeline');
            MockExposicion = jasmine.createSpy('MockExposicion');
            MockPost = jasmine.createSpy('MockPost');
            MockUsuario = jasmine.createSpy('MockUsuario');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Timeline': MockTimeline,
                'Exposicion': MockExposicion,
                'Post': MockPost,
                'Usuario': MockUsuario
            };
            createController = function() {
                $injector.get('$controller')("TimelineDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'expoCrApp:timelineUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
