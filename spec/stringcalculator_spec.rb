require 'spec_helper'
require 'java'
require './target/my-app-1.0-SNAPSHOT.jar'
java_import 'org.kata.stringcalculator.App'
java_import 'org.kata.stringcalculator.NegativesNotAllowedException'

shared_examples "step1" do |f|
	it "should handle 0, 1 or 2 numbers" do
		expect(f.call '').to eq 0
		expect(f.call '1').to eq 1
		expect(f.call '1,2').to eq 3
		expect(f.call '2,3').to eq 5
	end
end

shared_examples "step2" do |f|
	it "should handle any amount of numbers" do
		expect(f.call '0,0,0,0,0,0,0,0,0,0,0,0,0,0').to eq 0
		expect(f.call '1,0,0,0,0,0,0,0,0,0,0,0,0,0').to eq 1
		expect(f.call '1,1,1,1,1,0,0,0,0,0,0,0,0,0').to eq 5
		expect(f.call '1,10,100').to eq 111
	end
end

shared_examples "step3" do |f|
	it "should handle any amount of numbers" do
		expect(f.call "1\n2,3").to eq 6
		expect(f.call "1\n2\n3").to eq 6
		expect(f.call "1\n2\n3,4").to eq 10
	end
end

shared_examples "step4" do |f|
	it "should support custom delimiter" do
		expect(f.call "//;\n1;2").to eq 3
		expect(f.call "//;|,\n1;2,3,4").to eq 10
		expect(f.call "//\\n\n1\n2\n3\n4").to eq 10
		expect(f.call "//\\n|;\n1;2\n3;4").to eq 10
	end
end

shared_examples "step5" do |f|
	it "should not allow negatives" do
		expect { f.call '1,4,-1' }.to raise_error(NegativesNotAllowedException)

		begin
			f.call '-1,-2,-3'
			fail "NegativesNotAllowedException not thrown"
		rescue NegativesNotAllowedException => e
			expect(e.getMessage).to start_with("negatives not allowed:")
			expect(e.getMessage).to include('-1')
			expect(e.getMessage).to include('-2')
			expect(e.getMessage).to include('-3')
		end
	end
end

shared_examples "step6" do |f|
	it "should ignore big numbers" do
		expect(f.call '1001').to eq 0
		expect(f.call '1000').to eq 1000
		expect(f.call '1,2,1001').to eq 3
	end
end

describe 'org.kata.stringcalculator.App#addStep1' do
	include_examples 'step1', -> (x) { App.addStep1 x }
end

describe 'org.kata.stringcalculator.App#addStep2' do
	f = -> (x) { App.addStep2 x }
	include_examples 'step1', f
	include_examples 'step2', f
end

describe 'org.kata.stringcalculator.App#addStep3' do
	f = -> (x) { App.addStep3 x }
	include_examples 'step1', f
	include_examples 'step2', f
	include_examples 'step3', f
end

describe 'org.kata.stringcalculator.App#addStep4' do
	f = -> (x) { App.addStep4 x }
	include_examples 'step1', f
	include_examples 'step2', f
	include_examples 'step3', f
	include_examples 'step4', f
end

describe 'org.kata.stringcalculator.App#addStep5' do
	f = -> (x) { App.addStep5 x }
	include_examples 'step1', f
	include_examples 'step2', f
	include_examples 'step3', f
	include_examples 'step4', f
	include_examples 'step5', f
end

describe 'org.kata.stringcalculator.App#addStep6' do
	f = -> (x) { App.addStep6 x }
	include_examples 'step1', f
	include_examples 'step2', f
	include_examples 'step3', f
	include_examples 'step4', f
	include_examples 'step5', f
	include_examples 'step6', f
end
