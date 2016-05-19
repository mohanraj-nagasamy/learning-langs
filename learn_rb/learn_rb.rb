@openers = ['(', '{', '[']
@closers = [')', '}', ']']

@mapping = {"(" => ")", "{" => "}", "["  => "]"}.invert

# puts "mapping = #{@mapping}"

def validate(str)
  queue = []
  str.split.each do |s|
    if @openers.include?(s)
      queue.push s
      next
    end

    if @closers.include?(s)
      if queue[-1] == @mapping[s]
        queue.pop
      end
    end
  end
  puts "queue = #{queue}"
  queue.size == 0
end

puts "{ [ ] ( ) } = #{validate('{ [ ] ( ) }')}"
puts "{ [ ( ] ) } = #{validate('{ [ ( ] ) }')}"
puts "{ [ } = #{validate('{ [ }')}"