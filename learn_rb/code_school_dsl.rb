module ProcTest
  module Test1
    extend self

    def call_me_twice
      yield
      yield
    end

    def run
      call_me_twice { puts "call_me_twice" }

      my_proc = Proc.new { puts 'tweet' }
      puts my_proc.class

      my_proc = Proc.new do |variable|
        puts "tweet with #{variable}"
      end
      puts my_proc.class
      # my_proc.call(4343)

      my_proc = lambda { |variable|  puts 'tweet' }
      puts my_proc.class

      my_proc = -> { puts 'tweet' }
      puts my_proc.class

      my_proc.call
    end
  end
  
################################
  module Test2
    extend self
    
    class Tweet
      def initialize(msg)
      end

      def post
        if authenticate?
          yield
        else
          raise "Auth Error"
        end
      end

      def authenticate?
        true
      end
    end

    def run
      tweet = Tweet.new("Buby bits!")
      tweet.post { puts 'Sent!' }
    end    
  end

  module Test3
    extend self
    
    class Tweet
      def initialize(msg)
      end

      def post(success, error)
        if authenticate?
          success.call
        else
          error.call
        end
      end

      def authenticate?
        true
      end
    end

    def run
      tweet = Tweet.new("Buby bits!")
      success = -> { puts "#{self} Sent!" }
      error = -> { raise 'Auth Error' }
      tweet.post success, error
    end    
  end

  module Test4
    extend self
    
    class Timeline
      attr_accessor :tweets

      def initialize(tweets)
        @tweets = tweets
      end

      def each
        @tweets.each { |e| yield e  }
      end

    end

    def run
      t = Timeline.new(['Tweet one', 'Tweet two'])
      printer = lambda { |e| puts e  }
      # converts lambda to Block
      t.each(&printer)
    end    
  end

  module Test5
    extend self
    
    class Timeline
      attr_accessor :tweets

      def initialize(tweets)
        @tweets = tweets
      end
      
      # converts block to Proc object
      def each(&block)
        puts "#{self} #{block.class}"
        @tweets.each { |e| block.call(e)  }
      end

    end

    def run
      t = Timeline.new(['Tweet three', 'Tweet four'])
      printer = lambda { |e| puts e  }
      # converts lambda to Block
      t.each(&printer)
    end    
  end

  module Test6
    extend self
    
    class Timeline
      attr_accessor :tweets

      def initialize(tweets)
        @tweets = tweets
      end

      # converts block to Proc object
      def each(&block)
        puts "#{self} #{block.class}"
        # converts Proc object to Block
        @tweets.each(&block)
      end

    end

    def run
      t = Timeline.new(['Tweet five', 'Tweet six'])
      printer = lambda { |e| puts e  }
      # converts lambda to Block
      t.each(&printer)
    end    
  end

  module Test7
    extend self
    
    class Timeline
      include Enumerable
      
      def each(&block)
        
      end
      attr_accessor :tweets

      def initialize(tweets)
        @tweets = tweets
      end

      # converts block to Proc object
      def each(&block)
        # converts Proc object to Block
        @tweets.each(&block)
      end

    end

    def run
      t = Timeline.new(['Tweet five', 'Tweet six'])
      # symbol to proc object
      upcase = lambda { |e|  e.upcase }
      upcase = Proc.new { |e|  e.upcase }

      puts t.map()
    end    
  end

end

# ProcTest::Test1.run
# ProcTest::Test2.run

# ProcTest::Test3.run
# ProcTest::Test4.run
# ProcTest::Test5.run
# ProcTest::Test6.run
# ProcTest::Test7.run


module SelfTest
  module Test1
    extend self

      class Tweet
        attr_accessor :status, :created_at
        
        def initialize(status)
          @status = status
          @created_at = Time.now
        end
      end

      class Tweet
        attr_accessor :user
      end

      def run
        # Tweet.class_eval do
        #   attr_accessor :user
        # end
        t =  Tweet.new("testsasdf aslkdjasdjfkljaslkdfjs a")
        t.user = "mohanraj"

        p t
      end
  end
end
module SelfTest
  module Test2
    extend self

      class Tweet
        attr_accessor :user, :status
        def say_hi
          puts "hello \n"
        end
      end

      class MethodLogger
        def log_method(klass, method_name)
          klass.class_eval do
            method_name_original = "#{method_name}_original"
            alias_method method_name_original, method_name
            define_method method_name do
              puts "#{Time.now}: Called #{method_name} "
              send method_name_original
            end
          end
        end
      end

      def run
        MethodLogger.new.log_method(Tweet, :say_hi)
        t = Tweet.new
        t.say_hi
        p t
        t.instance_eval do
          self.status = "Change tweets status"
        end

        p t
      end
  end
end

module SelfTest
  module Test3
    extend self

      # class Tweet
      #   attr_accessor :user, :status
        
      #   def initialize
      #     yield self if block_given?
      #   end
      # end

      class Tweet
        attr_accessor :user, :status
        
        def initialize(&block)
          instance_eval(&block) if block_given?
        end
      end

      def run
        t = Tweet.new
        # p t
        # t = Tweet.new do |t|
        #   t.user = 'mohanraj'
        #   t.status = 'Change tweets'
        # end
        # p t
        t = Tweet.new do
          self.user = 'mohanraj'
          @status = 'Change tweets'
        end
        p t
      end
  end
end

# SelfTest::Test1.run
# SelfTest::Test2.run
# SelfTest::Test3.run

module MethodMissingTest
  module Test1
    extend self

      class Tweet
        def initialize(text)
          @text = text
        end

        def method_missing(meth, *args, &blk)
          match = meth.to_s.match(/^hash_(\w+)/)
          p "match: #{match}"
          if match
            self.class.class_eval do
              define_method(meth) do

                @text = @text + " #" + match[1]
              end
            end
            send(meth)
          else
            super
          end
        end
      end

      def run
        t = Tweet.new("Here is a tweet")
        p t.hash_submit
        p t.hash_submit
        p t.hash_shipit
        p t.hash_shipit
      end
  end
end

# MethodMissingTest::Test1.run


class Library
  SYSTEMS = ['arcade', 'atari', 'pc']

  attr_accessor :games

  def method_missing(name, *args)
    system = name.to_s
    if SYSTEMS.include? system
      self.class.class_eval do
        define_method system do
          find_by_system(system)
        end
      end
      send(system)
    else
      super
    end
  end

  private

  def find_by_system(system)
    games = []
    games.select { |game| game.system == system }
  end
end

# Library.new.atari


module DSLTest
  module Test1
    extend self

      class Tweet
        def initialize(user)
          @user = user
          @tweets = []
        end

        def mention(arg)
          @tweets << "@#{arg}"
        end
          
        def text(arg)
          @tweets << "@#{arg}"
        end
          
        def hashtag(arg)
          @tweets << "##{arg}"
        end
          
        def link(arg)
          @tweets << "::#{arg}"
        end

        def submit_to_twitter
          tweet_text = @tweets.join(" ")
          puts tweet_text
        end
      end

      def tweet_as(user, &block)
        t = Tweet.new(user)
        t.instance_eval(&block)
        t.submit_to_twitter
      end

      def run
        tweet_as 'markkendall' do
         mention 'codeschool'
         text 'I made a DSL!'
         hashtag 'hooray'
         hashtag 'ruby'
         link 'http://codeschool.com'
        end
      end
  end
end

module DSLTest
  module Test2
    extend self

      class Tweet
        def initialize(user)
          @user = user
          @tweets = []
        end

        def mention(arg)
          @tweets << "@#{arg}"
          self
        end
          
        def text(arg)
          @tweets << "@#{arg}"
          self
        end
          
        def hashtag(arg)
          @tweets << "##{arg}"
          self
        end
          
        def link(arg)
          @tweets << "::#{arg}"
          self
        end

        def submit_to_twitter
          tweet_text = @tweets.join(" ")
          puts tweet_text
        end
      end

      def tweet_as(user, &block)
        t = Tweet.new(user)
        t.instance_eval(&block)
        t.submit_to_twitter
      end

      def run
        tweet_as 'markkendall' do
         mention 'codeschool'
         text('I made a DSL!').hashtag( 'hooray').hashtag ('ruby')
         link 'http://codeschool.com'
        end
      end
  end
end

module DSLTest
  module Test3
    extend self

      class Tweet
        def initialize(user)
          @user = user
          @tweets = []
          @annotations = {}
        end

        def method_missing(meth, *args, &blk)
          @annotations[meth] = args.join(", ")
        end

        def mention(*args)
          args.each {|arg| @tweets << "@#{arg}"}
          self
        end
          
        def text(arg)
          @tweets << "@#{arg}"
          self
        end
          
        def hashtag(arg)
          @tweets << "##{arg}"
          self
        end
          
        def link(arg)
          @tweets << "::#{arg}"
          self
        end

        def submit_to_twitter
          tweet_text = @tweets.join(" ")
          puts tweet_text
          puts @annotations.inspect 
        end
      end

      def tweet_as(user, text = nil, &block)
        t = Tweet.new(user)
        t.text(text) if text
        t.instance_eval(&block) if block_given?
        t.submit_to_twitter
      end

      def run
        tweet_as 'markkendall', 'I made a DSL #hooray, #ruby'

        tweet_as 'markkendall' do
         mention 'codeschool', 'dsl'
         text('I made a DSL!').hashtag( 'hooray').hashtag ('ruby')
         link 'http://codeschool.com'
         lat 23432.234234
         lan 1134.2342343
         keywords 'ruby', 'dsl'
        end
      end
  end
end

# DSLTest::Test1.run
# DSLTest::Test2.run
DSLTest::Test3.run

